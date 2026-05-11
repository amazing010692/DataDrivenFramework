package io.github.amazing010692.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * ExtentManager - Configures and creates ExtentReports instances.
 * 
 * Provides a centralized configuration for HTML test reports
 * including theme, metadata, and system information.
 */
public class ExtentManager {

	private static ExtentReports extent;

	public static ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);

		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("Data Driven Framework - Test Report");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Author", "Janielle Joy Gregorio");
		extent.setSystemInfo("Framework", "Data Driven Framework");
		extent.setSystemInfo("Platform", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));

		return extent;
	}
}
