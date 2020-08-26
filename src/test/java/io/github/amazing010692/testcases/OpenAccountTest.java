package io.github.amazing010692.testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;

public class OpenAccountTest extends TestBase {
	
	@Test(dataProvider = "getData")
	public void openAccountTest(String customer, String currency) throws InterruptedException {
		
	}
	

}
