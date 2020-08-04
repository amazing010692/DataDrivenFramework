package io.github.amazing010692.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {
	
	public static void main(String[] args) throws IOException {
		
		System.out.println(System.getProperty("user.dir"));
		Properties config = new Properties();
		
		//Path of Config.properties file.
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		
		//Load the configuration file.
		config.load(fis);
		
		System.out.println(config.getProperty("browser"));
	}

	
}
