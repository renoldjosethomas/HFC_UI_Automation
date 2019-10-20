package clubkitchen.test;

import java.util.Dictionary;

import clubkitchen.pages.GeneralActions;
import clubkitchen.pages.FoodMenuActions;

public class AddItemToCartTest extends BaseTest{
	
	GeneralActions general;
	FoodMenuActions menuPage;
	

	public AddItemToCartTest() {
		general = new GeneralActions();
		menuPage = new FoodMenuActions();
	}

	public boolean addItemToCart(Dictionary<String, String> testData) {
		try {
			general.menuPage();
			menuPage.provideAddress(testData.get("Address"));
//			menuPage.chooseRestaurant(testData.get("Restaurant"));
			menuPage.chooseItem(testData.get("Category"), testData.get("Item"));
			
			return true;

		} catch (Exception driverEx) {
			exceptionMessage = driverEx.getMessage();
			
			return false;
		}	
	}

}
