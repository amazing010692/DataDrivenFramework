package io.github.amazing010692.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class TestProperties {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println(System.getProperty("user.dir"));
		Properties config = new Properties();
		
		//Path of Config.properties file.
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
	}

}
