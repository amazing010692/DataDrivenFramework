package io.github.amazing010692.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() throws InterruptedException {
		
		logger.info("Inside Login Test");
		click("bmlBtn");
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))), "Login not successful");
		
		logger.info("Login successfully executed");
		
		Assert.fail("Login not successful");

	}

}
