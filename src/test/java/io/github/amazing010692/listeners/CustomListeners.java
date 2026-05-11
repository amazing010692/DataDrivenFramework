package io.github.amazing010692.listeners;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.github.amazing010692.base.TestBase;
import io.github.amazing010692.utilities.ExtentManager;
import io.github.amazing010692.utilities.TestUtil;

/**
 * CustomListeners - TestNG event listener for ExtentReports integration.
 *
 * Hooks into test lifecycle events to:
 * - Create test entries in the report
 * - Log pass/fail/skip status
 * - Capture screenshots on failure
 */
public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	private static final String fileName = "Extent_" + DATE_FORMAT.format(new Date()) + ".html";
	private static final String reportPath = Paths.get(
			System.getProperty("user.dir"), "reports", fileName).toString();
	private static ExtentReports extent = ExtentManager.createInstance(reportPath);
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(
				result.getTestClass().getName() + "  @TestCase: " + result.getMethod().getMethodName());
		testReport.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String logText = "<b>TEST CASE: " + result.getMethod().getMethodName().toUpperCase() + " PASSED</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testReport.get().pass(m);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		testReport.get().fail("<details><summary><b><font color=red>Exception Occurred: Click to see"
				+ "</font></b></summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details>\n");

		try {
			TestUtil.captureScreenshot();
			testReport.get().fail("<b><font color=red>Screenshot of failure</font></b>",
					MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName).build());
		} catch (IOException e) {
			logger.error("Failed to capture screenshot on test failure", e);
		}

		Markup m = MarkupHelper.createLabel("TEST CASE FAILED", ExtentColor.RED);
		testReport.get().log(Status.FAIL, m);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String logText = "<b>Test Case: " + result.getMethod().getMethodName() + " Skipped</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		testReport.get().skip(m);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}

	@Override
	public void onStart(ISuite suite) {
	}

	@Override
	public void onFinish(ISuite suite) {
		if (extent != null) {
			extent.flush();
		}
	}
}
