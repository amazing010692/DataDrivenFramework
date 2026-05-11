package io.github.amazing010692.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;

/**
 * BankManagerLoginTest - Validates the Bank Manager login flow.
 * 
 * Verifies that clicking the Bank Manager Login button
 * navigates to the manager dashboard with expected elements.
 */
public class BankManagerLoginTest extends TestBase {

	@Test
	public void loginAsBankManager() throws InterruptedException, IOException {
		logger.info("Starting Bank Manager Login Test");
		click("bmlBtn_CSS");

		Assert.assertTrue(
				isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))),
				"Login not successful - Add Customer button not found");

		logger.info("Bank Manager login verified successfully");
	}
}
