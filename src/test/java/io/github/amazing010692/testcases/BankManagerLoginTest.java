package io.github.amazing010692.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() throws InterruptedException {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		logger.info("Inside Login Test");
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))), "Login not successful");
		
		logger.info("Login successfully executed");
		Reporter.log("Login successfully executed");
		Reporter.log("<a target=\"blank\" href=\"C:\\Users\\hello\\Pictures\\screenshots\\error.jpg\">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"blank\" href=\"C:\\Users\\hello\\Pictures\\screenshots\\error.jpg\"><img src=\"C:\\Users"
				+ "\\hello\\Pictures\\screenshots\\error.jpg\" height=200 width=200></img></a>");
	}

}
