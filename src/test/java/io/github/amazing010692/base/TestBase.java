package io.github.amazing010692.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {
	
	public static WebDriver driver;
	
	@BeforeSuite
	public void setUp() {
		
	}
	
	@AfterSuite
	public void tearDown() {
		
	}

}
