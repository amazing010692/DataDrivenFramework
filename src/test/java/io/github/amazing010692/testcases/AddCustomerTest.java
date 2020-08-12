package io.github.amazing010692.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;

public class AddCustomerTest extends TestBase {
	
	@Test(dataProvider = "getData")
	public void addCustomer(String firstName, String lastName, String postCode, String alertText) throws InterruptedException {
		driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();
		driver.findElement(By.cssSelector(OR.getProperty("firstname"))).sendKeys(firstName);
		driver.findElement(By.cssSelector(OR.getProperty("lastname"))).sendKeys(lastName);
		driver.findElement(By.cssSelector(OR.getProperty("postcode"))).sendKeys(postCode);
		driver.findElement(By.cssSelector(OR.getProperty("addBtn"))).click();
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(alertText));
		Thread.sleep(3000);
		alert.accept();
		
		logger.info("Successfully Added a Customer");
	}
	
	@DataProvider
	public Object[][] getData() {
		String sheetName = "AddCustomerTest";
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[rows - 1][cols];
		
		for (int rowNum = 2; rowNum <= rows; rowNum++) {	//2
			for (int colNum = 0; colNum < cols; colNum++) {
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);	//2
			}
		}
		return data;
	}

}
