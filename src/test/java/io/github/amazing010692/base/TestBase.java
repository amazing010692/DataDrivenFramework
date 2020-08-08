package io.github.amazing010692.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Log log;
	
	@BeforeSuite
	public void setUp() {
		if(driver == null) {		
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded !!!");
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
				log.debug("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("Chrome Launched !!!");
				
			} else if(config.getProperty("browser").equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.debug("Firefox Launched !!!");
				
			} else if(config.getProperty("browser").equals("ie")) {		
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				log.debug("IE Launched !!!");
				
			} else if(config.getProperty("browser").equals("edge")) {		
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				log.debug("Edge browser Launched !!!");
			
			} else if(config.getProperty("browser").equals("opera")) {		
				DesiredCapabilities capabilities = new DesiredCapabilities();
				OperaOptions options = new OperaOptions();
				options.setBinary("C:\\Users\\hello\\AppData\\Local\\Programs\\Opera\\64.0.3417.73\\opera.exe");
				capabilities.setCapability(OperaOptions.CAPABILITY, options);
				
				WebDriverManager.operadriver().setup();
				driver = new OperaDriver(options);
				log.debug("Opera Launched !!!");
			
			}
			
			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to: " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		}
	}
	
	@AfterSuite
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
		
		log.debug("Test Execution Completed !!!");
	}

}
