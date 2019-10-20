package clubkitchen.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class FailureScreenshotUtility 
{
	LogUtility log4j;
	
	public FailureScreenshotUtility() 
	{
		log4j = new LogUtility();
	}
	
	public String getScreenshot(WebDriver driver, String testName) throws Exception
	{
		try
		{
			 //Below line is just to append the date format with the screenshot name to avoid duplicate names 
			 String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			 TakesScreenshot ts = (TakesScreenshot) driver;
			 File source = ts.getScreenshotAs(OutputType.FILE);
			 String outputFilePath = "./failure screenshots/" + testName + " " + dateName + ".png";
			 File destination = new File(outputFilePath);
			 FileUtils.copyFile(source, destination);
			 return outputFilePath;
		}
		catch(Exception sysEx)
		{
			log4j.error("getScreenshot", sysEx.getMessage());
			return sysEx.getMessage();
		}
	}
}