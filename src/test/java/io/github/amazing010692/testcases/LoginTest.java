package io.github.amazing010692.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import io.github.amazing010692.base.TestBase;

public class LoginTest extends TestBase {
	
	@Test
	public void loginAsBankManager() throws InterruptedException {
		log.debug("Inside Login Test");
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		Thread.sleep(3000);
		log.debug("Login successfully executed");
	}

}
