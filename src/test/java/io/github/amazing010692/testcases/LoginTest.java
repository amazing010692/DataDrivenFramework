package io.github.amazing010692.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;

public class LoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() {
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
	}

}
