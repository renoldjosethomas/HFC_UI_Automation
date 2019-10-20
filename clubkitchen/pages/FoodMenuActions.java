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
	
	public void chooseItem(String category, String product) throws InterruptedException {
		Thread.sleep(3000);
		findElement(parentDriver,By.xpath(String.format(objCategory, category))).click();
		findElement(parentDriver,By.xpath(String.format(objProduct, product))).click();
		Thread.sleep(10000);
		report.nodeTestPass("Add Item To Cart");
	}
	
	public void provideAddress(String address) {
		if(findElement(parentDriver,By.xpath(objAddressToMenuButton)).isDisplayed()) {
			findElement(parentDriver,By.xpath(objAddressPopupInput)).sendKeys(address);
			findElement(parentDriver,By.xpath(objAddressToMenuButton)).click();
			report.nodeTestPass("Provide Address in the Address Pop-Up Modal");
		}
	}
	
}
