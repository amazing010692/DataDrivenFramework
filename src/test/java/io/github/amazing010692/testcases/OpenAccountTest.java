package io.github.amazing010692.testcases;

import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;
import io.github.amazing010692.utilities.TestUtil;

public class OpenAccountTest extends TestBase {
	
	@Test(dataProviderClass = TestUtil.class, dataProvider = "getData")
	public void openAccountTest(String customer, String currency) throws InterruptedException {
		
	}
	

}
