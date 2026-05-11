package io.github.amazing010692.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import io.github.amazing010692.base.TestBase;

/**
 * TestUtil - Utility class providing:
 * - Screenshot capture on failure
 * - Data provider for data-driven tests (reads from Excel)
 * - Test runnable check from Excel suite configuration
 */
public class TestUtil extends TestBase {

	public static String screenshotPath;
	public static String screenshotName;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

	public static void captureScreenshot() throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		screenshotName = "screenshot_" + DATE_FORMAT.format(new Date()) + ".jpg";
		String destPath = Paths.get(System.getProperty("user.dir"),
				"target", "surefire-reports", "html", screenshotName).toString();

		FileUtils.copyFile(srcFile, new File(destPath));
		logger.info("Screenshot captured: " + screenshotName);
	}

	@DataProvider(name = "dp")
	public Object[][] getData(Method m) {
		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < cols; colNum++) {
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;
	}

	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		String sheetName = "test_suite";
		int rows = excel.getRowCount(sheetName);
		for (int rNum = 2; rNum <= rows; rNum++) {
			String testCase = excel.getCellData(sheetName, "TCID", rNum);
			if (testCase.equalsIgnoreCase(testName)) {
				return excel.getCellData(sheetName, "Runmode", rNum).equalsIgnoreCase("Y");
			}
		}
		return false;
	}
}
