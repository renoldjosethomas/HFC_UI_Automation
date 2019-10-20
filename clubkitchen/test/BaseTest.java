package clubkitchen.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Dictionary;

import clubkitchen.pages.GeneralActions;

public class BaseTest {

	private GeneralActions general;
	public String exceptionMessage;

	protected String actionPage, actionKeyword, actionDescription;

	public BaseTest() {
		general = new GeneralActions();
	}

	public boolean login(String url, String username, String password) {
		try {
			general.performLogin(url, username, password);
			return true;

		} catch (Exception driverEx) {
			exceptionMessage = driverEx.getMessage();
			return false;
		}
	}

	public boolean logout() {
		try {
			general.performLogout();
			return true;

		} catch (Exception driverEx) {
			exceptionMessage = driverEx.getMessage();
			return false;
		}
	}
	
	public boolean executeTestCase(ArrayList<String> testDetails, Dictionary<String,String> testData)
	// Invoke methods using action_keyword in Test Case
	{
		boolean result = false;;
		try {
			
			Class<?> classRef = Class.forName("clubkitchen.test." + testDetails.get(0));
			
			// Dynamically create instances of classes
			Object classInst = (Object) classRef.newInstance();
			Method method[] = classInst.getClass().getMethods();
			for (int i = 0; i < method.length; i++) {
				if (method[i].getName().equalsIgnoreCase(testDetails.get(1))) {
					if (testData.isEmpty()) {
						// invoking page object methods without arguments
						System.out.println("Test Data Empty");
						Object value = method[i].invoke(classInst);
						result = (boolean) value;
						break;
					} else {
						// invoking page object methods with arguments
						Object value = method[i].invoke(classInst, testData);
						result = (boolean) value;
						break;
					}
				}
			}
			
			return result;
			
		} catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException
				| ClassNotFoundException sysEx) {
//			log4j.error("executeTestCase", sysEx.getMessage());
			return false;
		}
		
	}

}
