package io.github.amazing010692.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;
import io.github.amazing010692.utilities.TestUtil;

public class OpenAccountTest extends TestBase {
	
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(String customer, String currency) throws InterruptedException {
		click("openaccount_CSS");
		select("customer_CSS", data.get("customer"));
		select("currency_CSS", data.get("currency"));
		click("process_CSS");
		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		
	}
	

}
