package io.github.amazing010692.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.Status;

import io.github.amazing010692.listeners.CustomListeners;
import io.github.amazing010692.utilities.ExcelReader;
import io.github.amazing010692.utilities.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * TestBase - Foundation class for all test classes.
 * 
 * Responsibilities:
 * - WebDriver initialization and teardown
 * - Configuration loading (Config.properties, OR.properties)
 * - Reusable action methods (click, type, select)
 * - Logger initialization
 */
public class TestBase {

	private static final String BASE_DIR = System.getProperty("user.dir");
	private static final String RESOURCES_PATH = Paths.get(BASE_DIR, "src", "test", "resources").toString();

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static final Logger logger = LogManager.getLogger(TestBase.class.getName());
	public static ExcelReader excel = new ExcelReader(
			Paths.get(RESOURCES_PATH, "excel", "testdata.xlsx").toString());
	public static WebDriverWait wait;
	public static String browser;

	@BeforeSuite
	public void setUp() {
		if (driver == null) {
			initializeLogger();
			loadConfig();
			loadObjectRepository();
			initializeBrowser();
		}
	}

	private void initializeLogger() {
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		File logConfig = new File(Paths.get(RESOURCES_PATH, "logs", "log4j2.xml").toString());
		if (logConfig.exists()) {
			context.setConfigLocation(logConfig.toURI());
		}
	}

	private void loadConfig() {
		String configPath = Paths.get(RESOURCES_PATH, "properties", "Config.properties").toString();
		try (FileInputStream fis = new FileInputStream(configPath)) {
			config.load(fis);
			logger.info("Config file loaded successfully");
		} catch (IOException e) {
			logger.fatal("Failed to load Config.properties: " + e.getMessage());
			throw new RuntimeException("Cannot proceed without Config.properties", e);
		}
	}

	private void loadObjectRepository() {
		String orPath = Paths.get(RESOURCES_PATH, "properties", "OR.properties").toString();
		try (FileInputStream fis = new FileInputStream(orPath)) {
			OR.load(fis);
			logger.info("Object Repository loaded successfully");
		} catch (IOException e) {
			logger.fatal("Failed to load OR.properties: " + e.getMessage());
			throw new RuntimeException("Cannot proceed without OR.properties", e);
		}
	}

	private void initializeBrowser() {
		// Environment variable takes precedence over config file
		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
		} else {
			browser = config.getProperty("browser");
		}
		config.setProperty("browser", browser);

		switch (browser.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				if ("true".equalsIgnoreCase(System.getenv("HEADLESS"))) {
					chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
				}
				driver = new ChromeDriver(chromeOptions);
				logger.info("Chrome launched");
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				logger.info("Firefox launched");
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				logger.info("Edge launched");
				break;
			default:
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				logger.warn("Browser '{}' not recognized. Defaulting to Chrome.", browser);
				break;
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(
				Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		driver.get(config.getProperty("testsiteurl"));
		logger.info("Navigated to: " + config.getProperty("testsiteurl"));
		wait = new WebDriverWait(driver, 5);
	}

	/**
	 * Resolves a locator from OR.properties and finds the element.
	 */
	private WebElement findElement(String locator) {
		String value = OR.getProperty(locator);
		if (locator.endsWith("_CSS")) {
			return driver.findElement(By.cssSelector(value));
		} else if (locator.endsWith("_XPATH")) {
			return driver.findElement(By.xpath(value));
		} else if (locator.endsWith("_ID")) {
			return driver.findElement(By.id(value));
		}
		throw new IllegalArgumentException("Locator suffix not recognized: " + locator
				+ ". Use _CSS, _XPATH, or _ID.");
	}

	public void click(String locator) {
		findElement(locator).click();
		CustomListeners.testReport.get().log(Status.INFO, "Clicking on: " + locator);
	}

	public void type(String locator, String value) {
		findElement(locator).sendKeys(value);
		CustomListeners.testReport.get().log(Status.INFO, "Typing in: " + locator + " value: " + value);
	}

	public void select(String locator, String value) {
		WebElement dropdown = findElement(locator);
		new Select(dropdown).selectByVisibleText(value);
		CustomListeners.testReport.get().log(Status.INFO, "Selecting from: " + locator + " value: " + value);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void verifyEquals(String expected, String actual) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable t) {
			TestUtil.captureScreenshot();
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			Reporter.log("<br>Verification failure: " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src="
					+ TestUtil.screenshotName + " height=200 width=200></img></a>");
			CustomListeners.testReport.get().log(Status.FAIL,
					"Verification failed: " + t.getMessage());
		}
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			logger.info("Test execution completed - browser closed");
		}
	}
}
