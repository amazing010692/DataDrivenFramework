package io.github.amazing010692.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

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
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.Status;

import io.github.amazing010692.listeners.CustomListeners;
import io.github.amazing010692.utilities.ExcelReader;
import io.github.amazing010692.utilities.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * TestBase - Foundation class for all test classes.
 *
 * Responsibilities:
 * - WebDriver initialization and teardown (ThreadLocal for parallel safety)
 * - Configuration loading (Config.properties, OR.properties)
 * - Reusable action methods (click, type, select)
 * - Logger initialization
 */
public class TestBase {

	private static final String BASE_DIR = System.getProperty("user.dir");
	private static final String RESOURCES_PATH = Paths.get(BASE_DIR, "src", "test", "resources").toString();

	private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static final Logger logger = LogManager.getLogger(TestBase.class.getName());
	public static ExcelReader excel = new ExcelReader(
			Paths.get(RESOURCES_PATH, "excel", "testdata.xlsx").toString());
	public static String browser;

	public static WebDriver getDriver() {
		return driverThreadLocal.get();
	}

	@BeforeTest
	public void setUp() {
		initializeLogger();
		loadConfig();
		loadObjectRepository();
		initializeBrowser();
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
		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
		} else {
			browser = config.getProperty("browser");
		}
		config.setProperty("browser", browser);
		boolean headless = "true".equalsIgnoreCase(System.getenv("HEADLESS"));

		WebDriver driver;
		switch (browser.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				if (headless) {
					chromeOptions.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
				}
				driver = new ChromeDriver(chromeOptions);
				logger.info("Chrome launched");
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				if (headless) {
					firefoxOptions.addArguments("--headless");
				}
				driver = new FirefoxDriver(firefoxOptions);
				logger.info("Firefox launched");
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				EdgeOptions edgeOptions = new EdgeOptions();
				if (headless) {
					edgeOptions.addArguments("--headless=new");
				}
				driver = new EdgeDriver(edgeOptions);
				logger.info("Edge launched");
				break;
			default:
				WebDriverManager.chromedriver().setup();
				ChromeOptions defaultOptions = new ChromeOptions();
				if (headless) {
					defaultOptions.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
				}
				driver = new ChromeDriver(defaultOptions);
				logger.warn("Browser '{}' not recognized. Defaulting to Chrome.", browser);
				break;
		}

		driverThreadLocal.set(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(
				Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
		driver.get(config.getProperty("testsiteurl"));
		logger.info("Navigated to: " + config.getProperty("testsiteurl"));
	}

	/**
	 * Resolves a locator from OR.properties and finds the element.
	 */
	private WebElement findElement(String locator) {
		String value = OR.getProperty(locator);
		if (locator.endsWith("_CSS")) {
			return getDriver().findElement(By.cssSelector(value));
		} else if (locator.endsWith("_XPATH")) {
			return getDriver().findElement(By.xpath(value));
		} else if (locator.endsWith("_ID")) {
			return getDriver().findElement(By.id(value));
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
			getDriver().findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public WebDriverWait getWait() {
		return new WebDriverWait(getDriver(), Duration.ofSeconds(5));
	}

	public static void verifyEquals(String expected, String actual) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			TestUtil.captureScreenshot();
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			Reporter.log("<br>Verification failure: " + e.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src="
					+ TestUtil.screenshotName + " height=200 width=200></img></a>");
			CustomListeners.testReport.get().log(Status.FAIL,
					"Verification failed: " + e.getMessage());
			throw e;
		}
	}

	@AfterTest
	public void tearDown() {
		WebDriver driver = getDriver();
		if (driver != null) {
			driver.quit();
			driverThreadLocal.remove();
			logger.info("Test execution completed - browser closed");
		}
	}
}
