package io.github.amazing010692.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.github.amazing010692.base.TestBase;

public class TestUtil extends TestBase {
	
	public static String screenshotPath;
	public static void captureScreenshot() throws IOException {
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\error.jpg"));
	}

}
