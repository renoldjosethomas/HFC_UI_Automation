package clubkitchen.utils;

import org.apache.log4j.Logger;
import clubkitchen.utils.LogUtility;


public class LogUtility 
{
	private static Logger log;
	
	public LogUtility()
	{
		log = Logger.getLogger(LogUtility.class.getName());
	}
	
		
	public void info(String logMessage)
	{
		log.info(logMessage);
	}

	public void error(String methodName, String errorMessage) 
	{
		log.error("FAILED:  " + methodName + "   -   " + errorMessage);
	}

}