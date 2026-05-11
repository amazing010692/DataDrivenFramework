package io.github.amazing010692.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;
import io.github.amazing010692.utilities.TestUtil;

/**
 * AddCustomerTest - Data-driven test for adding bank customers.
 * 
 * Test data is read from the "addCustomerTest" sheet in testdata.xlsx.
 * Each row represents a different customer to add.
 */
public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(String firstName, String lastName, String postCode, String alertText)
			throws InterruptedException {
		click("addCustBtn_CSS");
		type("firstname_CSS", firstName);
		type("lastname_XPATH", lastName);
		type("postcode_CSS", postCode);
		click("addBtn_CSS");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(alertText),
				"Expected alert to contain: " + alertText);
		alert.accept();

		logger.info("Successfully added customer: " + firstName + " " + lastName);
		Thread.sleep(1000);
	}
}
