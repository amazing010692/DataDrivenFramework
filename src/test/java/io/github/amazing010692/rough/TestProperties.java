package io.github.amazing010692.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class TestProperties {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println(System.getProperty("user.dir"));
		Properties Config = new Properties();
		
		//Path of Config.properties file.
		FileInputStream fis = new FileInputStream("C:\\Users\\hello\\Documents\\Java_Tests\\DataDrivenFramework\\src\\test\\resources\\properties\\Config.properties");
	}

}
