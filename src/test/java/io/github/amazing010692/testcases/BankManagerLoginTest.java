package io.github.amazing010692.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() throws InterruptedException {
		logger.info("Inside Login Test");
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))), "Login not successful");
		
		logger.info("Login successfully executed");
		Reporter.log("Login successfully executed");
		Reporter.log("<a href=\"C:\\Users\\hello\\Pictures\\screenshots\\error.jpg\">Screenshot</a>");
	}

}
