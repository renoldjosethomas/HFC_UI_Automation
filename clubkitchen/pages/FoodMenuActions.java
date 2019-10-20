package clubkitchen.pages;

import org.openqa.selenium.By;

import clubkitchen.utils.ExtentReportsUtility;

public class FoodMenuActions extends BaseAction {
	// Path of Page Objects
	private String objAddressPopupInput = "//form/input[@placeholder='STRASSE, HAUSNUMMER']";
	private String objAddressToMenuButton = "//input[@type='submit' and @value='zum menü']";
	private String objChooseRestaurant = null;
	private String objCategory = "//div/span[text()='%1$s']";
	private String objProduct = "//a[@class='product--title' and @title='%1$s']/ancestor::form[@method='post']/parent::div[@class='product--info']//button[@class='buybox--button--image-overlay']";
	
	private ExtentReportsUtility report;

	public FoodMenuActions() {
		report = new ExtentReportsUtility();
	}

	public void chooseRestaurant(String restaurantName) {
		if (restaurantName == "Mamacita") {
			objChooseRestaurant = "//a[@title='Mamacita' and @data-sl='4']";
			findElement(parentDriver, By.xpath(objChooseRestaurant));
			report.nodeTestPass("Choose Restaurant");
		}
	}
	
	public void chooseItem(String category, String product) {
		String x = String.format(objCategory, category);
		findElement(parentDriver,By.xpath(x)).click();
		String y = String.format(objProduct, product);
		findElement(parentDriver,By.xpath(y)).click();
		report.nodeTestPass("Choose Item");
	}
	
	public void provideAddress(String address) {
		findElement(parentDriver,By.xpath(objAddressPopupInput)).sendKeys(address);
		findElement(parentDriver,By.xpath(objAddressToMenuButton)).click();
		report.nodeTestPass("Provide Address in the Address Pop-Up Modal");
	}
	
}
