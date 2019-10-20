package clubkitchen.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsUtility {

	private LogUtility log4j;
	private ExcelUtility excel;
	private ExtentReports extent;
	private static ExtentTest test;
	private FailureScreenshotUtility capture;
	private ExtentHtmlReporter htmlReporter;
//	private ExtentLoggerReporter config;

	public ExtentReportsUtility() {
		log4j = new LogUtility();
		excel = new ExcelUtility();
		capture = new FailureScreenshotUtility();
	}

	public void setExtentReportsConfig(String pathHTML, String fileXML) {
		htmlReporter = new ExtentHtmlReporter(new File(pathHTML));
//		config = new ExtentLoggerReporter("./src/");
//		config.loadXMLConfig(fileXML);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setDocumentTitle("CASMEX Test Automation Report"); 
		htmlReporter.config().setReportName("End to End Test Scenario"); 
		htmlReporter.config().setTheme(Theme.DARK);	
	}

	public void logExtentTestDetails(int fromRow, int toRow) {
		// Log Test Details like Sprint, Author, etc.. in Extent Report
		try {
			for (int data = fromRow; data <= toRow; data++) {
				extent.setSystemInfo(excel.getCellData(data, 0), excel.getCellData(data, 1));
			}
		} catch (Exception sysEx) {
			log4j.error("logExtentTestDetails", sysEx.getMessage());
		}
	}

	public void testPass(String details) {
		// Log PASS in Extent Report with the Pass Message
		test.pass(details);
	}

	public void testFail(WebDriver driverInst, String errorMessage, String testName)
	// Log Screenshot in Extent Report using the Screenshot File path
	{
		try {
			log4j.error(testName, errorMessage);

			String dateTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driverInst;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String screenshot = "./" + testName + " " + dateTime;
			File destination = new File(screenshot);
			FileUtils.copyFile(source, destination);

			// extentLogger.log(Status.FAIL, info);
			test.fail("TEST FAILED" + errorMessage
					+ test.addScreenCaptureFromPath(capture.getScreenshot(driverInst, testName)));
		} catch (Exception sysEx) {
			log4j.error("logExtentFail", sysEx.getMessage());
		}
	}

	public void logTestCase(String testName) {
		// Log PASS in Extent Report with the Pass Message
		test = extent.createTest(testName);
		log4j.info("Executing Test -  " + testName);
	}
	
	public void nodeTestPass(String nodeTestDescription) {
		test.createNode(nodeTestDescription).pass("Pass");
		log4j.info("Action - " + nodeTestDescription);;
	}

	public void endExtentReports() {
		// flush() - to write or update test information to your report.
		extent.flush();
	}
}