package io.github.amazing010692.utilities;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.github.amazing010692.base.TestBase;

public class TestUtil extends TestBase {
	
	public static void captureScreenshot() {
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	}

}
