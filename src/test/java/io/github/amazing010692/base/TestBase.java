package io.github.amazing010692.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
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

public class TestBase {
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static final Logger logger = LogManager.getLogger(TestBase.class.getName());
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	
	@BeforeSuite
	public void setUp() {
		if(driver == null) {
			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			File file = new File("./src/test/resources/logs/log4j2.xml");
			context.setConfigLocation(file.toURI());
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				logger.info("Config file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				logger.info("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				logger.info("Chrome Launched !!!");
				
			} else if(config.getProperty("browser").equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				logger.info("Firefox Launched !!!");
				
			} else if(config.getProperty("browser").equals("ie")) {		
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				logger.info("IE Launched !!!");
				
			} else if(config.getProperty("browser").equals("edge")) {		
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				logger.info("Edge browser Launched !!!");
			
			} else if(config.getProperty("browser").equals("opera")) {		
				DesiredCapabilities capabilities = new DesiredCapabilities();
				OperaOptions options = new OperaOptions();
				options.setBinary("C:\\Users\\hello\\AppData\\Local\\Programs\\Opera\\64.0.3417.73\\opera.exe");
				capabilities.setCapability(OperaOptions.CAPABILITY, options);
				
				WebDriverManager.operadriver().setup();
				driver = new OperaDriver(options);
				logger.info("Opera Launched !!!");
			
			}
			
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			driver.get(config.getProperty("testsiteurl"));
			logger.info("Navigated to: " + config.getProperty("testsiteurl"));
			wait = new WebDriverWait(driver, 5);
		}
	}
	
	public void click(String locator) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		CustomListeners.testReport.get().log(Status.INFO, "Clicking on : " + locator);
	}

	public void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}

		CustomListeners.testReport.get().log(Status.INFO, "Typing in : " + locator + " entered value as " + value);

	}
	
	static WebElement dropdown;

	public void select(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		CustomListeners.testReport.get().log(Status.INFO, "Selecting from dropdown : " + locator + " value as " + value);

	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public static void verifyEquals(String expected, String actual) throws IOException {

		try {

			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a href = TestUtil.screenshotName target = \"_blank\">Screenshot Link</a>");
			Reporter.log("<br>");
			Reporter.log("<a href = TestUtil.screenshotName target = \"_blank\">"
					+ "<img height= 200 width = 200 src = TestUtil.screenshotName></a>");
			/**Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");***/
			// Extent Reports
			CustomListeners.testReport.get().log(Status.FAIL, " Verification failed with exception : " + t.getMessage());
			//CustomListeners.testReport.get().log(Status.FAIL, CustomListeners.testReport.get().addScreenCaptureFromPath(TestUtil.screenshotName));

		}

	}
	
	@AfterSuite
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
		
		logger.info("Test Execution Completed !!!");
	}

}
