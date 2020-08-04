package io.github.amazing010692.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {
	
	public static void main(String[] args) throws IOException {
		
		System.out.println(System.getProperty("user.dir"));
		Properties config = new Properties();
		Properties OR = new Properties();
		
		//Path of Config.properties file.
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		
		//Load the configuration file.
		config.load(fis);
		
		//Path of OR.properties file.
		fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
		
		//Load the OR file.
		OR.load(fis);
		
		//You can use below to call the locator from OR.properties file.
		//driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		
		System.out.println(config.getProperty("browser"));
		System.out.println(OR.getProperty("bmlBtn"));	
	}

	
}
