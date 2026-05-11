package io.github.amazing010692.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;
import io.github.amazing010692.utilities.TestUtil;

/**
 * OpenAccountTest - Data-driven test for opening bank accounts.
 * 
 * Test data is read from the "openAccountTest" sheet in testdata.xlsx.
 * Each row represents a customer-currency combination.
 */
public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(String customer, String currency) throws InterruptedException {
		click("openaccount_CSS");
		select("customer_CSS", customer);
		select("currency_CSS", currency);
		click("process_CSS");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();

		logger.info("Successfully opened account for: " + customer + " with currency: " + currency);
		Thread.sleep(1000);
	}
}
