package io.github.amazing010692.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() throws InterruptedException, IOException {
		
		verifyEquals("abc", "xyz");
		Thread.sleep(3000);
		logger.info("Inside Login Test");
		click("bmlBtn_CSS");
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successful");
		
		logger.info("Login successfully executed");
		
		Assert.fail("Login not successful");

	}

}