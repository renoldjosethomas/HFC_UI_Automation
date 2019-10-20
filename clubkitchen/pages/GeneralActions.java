package clubkitchen.pages;

import org.openqa.selenium.By;

import clubkitchen.utils.ExtentReportsUtility;

public class GeneralActions extends BaseAction {

	// Path of Page Objects
	private String objAcceptCookieButton = "//button[@class='agree-cookie' and text()='Ok']";
	private String objLoginLink = "//div/a/i[@class='icon--lock']";
	private String objEmail = "//div/input[@placeholder='E-Mail-Adresse']";
	private String objPassword = "//div/input[@placeholder='Passwort']";
	private String objLogin = "//button[text()='LOGIN ']";
	private String objMyAccount = "//span[@class='account--display navigation--personalized']";
	private String objMyAccountSignOut = "//span[text()='Abmelden']";

	private String objHomePage = "//span[@id='lblExchangeHeader']";
	private String objAccMenu = "//a/i[@class='icon--account']";
	private String objFoodMenu = "//span[text()='Zur Speisekarte' and @class='btn__text']";
	
	private ExtentReportsUtility report;
	
	public GeneralActions() {
		report = new ExtentReportsUtility();
	}

	public void performLogin(String url, String email, String password) throws InterruptedException {
		parentDriver.get(url);
		findElement(parentDriver, By.xpath(objLoginLink)).click();
		findElement(parentDriver, By.xpath(objAcceptCookieButton)).click();
		findElement(parentDriver, By.xpath(objEmail)).sendKeys(email);
		findElement(parentDriver, By.xpath(objPassword)).sendKeys(password);
		findElement(parentDriver, By.xpath(objLogin)).click();
		Thread.sleep(3000);
	}

	public void performLogout() throws InterruptedException {
		findElement(parentDriver, By.xpath(objMyAccount)).click();
		findElement(parentDriver, By.xpath(objMyAccountSignOut)).click();
		
		Thread.sleep(3000);
	}
	
	//Access Profile Menu / Account-Menu Drop-Down
	public void accountMenu() {
		findElement(parentDriver, By.xpath(objAccMenu)).click();
		report.nodeTestPass("Click Account-Menu");
	}
	
	public void homePage() {
		findElement(parentDriver, By.xpath(objHomePage)).click();
		report.nodeTestPass("Navigate to Home Page");
	}
	
	public void menuPage() {
		findElement(parentDriver, By.xpath(objFoodMenu)).click();
		report.nodeTestPass("Navigate to Food Menu Page");
	}
	
	public void closeBrowser() {
		parentDriver.close();
	}

}