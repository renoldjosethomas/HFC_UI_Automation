package clubkitchen.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Properties;

import clubkitchen.pages.BaseAction;
import clubkitchen.utils.EmailUtility;
import clubkitchen.utils.ExcelUtility;
import clubkitchen.utils.ExtentReportsUtility;

public class TestExecutionUnit {
	private static Properties configFile;
	
	
	public static void main(String args[]) {
		// Initializing Instance of the following classes to use their methods
		
		BaseTest testBase = new BaseTest();
		BaseAction actBase = new BaseAction();
		EmailUtility mail = new EmailUtility();
		ExcelUtility excel = new ExcelUtility();
		ExtentReportsUtility report = new ExtentReportsUtility();
		
		loadConfigFile(configFile = new Properties());

		// Configure Selenium WebDriver
		actBase.initDriverInstance(configFile.getProperty("driverKey"),
															configFile.getProperty("driverPath"));

		// Configure Excel
		excel.setExcelConfig(configFile.getProperty("excelFilePath"), 
							configFile.getProperty("excelSheetName"));

		// Configure Extent Reports
		report.setExtentReportsConfig(configFile.getProperty("reportsFilePath"),
									configFile.getProperty("reportsConfigFile"));

		// Log Test Details in log4j file and Extent Report
		excel.logTestDetails(7, 13);
		report.logExtentTestDetails(7, 13);

		// Iterate through test cases and execute them step by step
		for (int rowNum = 17; rowNum <= ExcelUtility.lastRowNumber; rowNum += 2) {

			// Retrieve Test Details - Page / Test Name / Test Case Description
			ArrayList<String> testDetails = excel.getRowData(rowNum, 0, 3);

			// Retrieve Test Data Parameters
			Dictionary<String,String> testData = excel.getTestData(rowNum, 3);
			
			// Login
			testBase.login(configFile.getProperty("url"),
					configFile.getProperty("username"),
					configFile.getProperty("password"));
			
			// Log test case in the report (Test Case Name)
			report.logTestCase(testDetails.get(2));
			
			// Execute Test Case
			if(testBase.executeTestCase(testDetails, testData) == true)
				report.testPass("Test Passed");
			else
				report.testFail(BaseAction.parentDriver, testBase.exceptionMessage, testDetails.get(2));
			
			//Logout
			testBase.logout();
		}
		
		report.endExtentReports();
		mail.sendEmailReport();
		System.out.println("--------- UI AUTOMATION SUITE SUCCESSFULLY RUN ---------");
	}

	private static void loadConfigFile(Properties configFile) {
		try {
			InputStream file = new FileInputStream("./config.properties");
			configFile.load(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}