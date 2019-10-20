package clubkitchen.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseAction {

	public static WebDriver parentDriver;
	public static Actions parentAction;
	public static Select parentSelect;
	public static WebDriverWait parentWait;
	public static FluentWait<WebDriver> parentFluentWait;
	public static ArrayList<String> browserTabs;

	public static WebDriver childDriver;
	public static Actions childAction;
	public static Select childSelect;
	public static WebDriverWait childWait;
	public static FluentWait<WebDriver> childFluentWait;

	public void initDriverInstance(String key, String path) {
		// Initialize browser.
		System.setProperty(key, path);
		parentDriver = new ChromeDriver();
		parentDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		parentDriver.manage().window().maximize();
	}

	public WebElement findElement(WebDriver driver, By by) {
		WebElement element = null;
		try {
			Thread.sleep(1600);
			if (driver == parentDriver) {
				element = parentDriver.findElement(by);
				parentWait.until(ExpectedConditions.visibilityOf(element));
				parentFluentWait.until(ExpectedConditions.elementToBeClickable(element));
			} else if (driver == childDriver) {
				element = childDriver.findElement(by);
				childWait.until(ExpectedConditions.visibilityOf(element));
				childFluentWait.until(ExpectedConditions.elementToBeClickable(element));
			}
			return element;
		} catch (Exception sysEx) {
			return element;
		}
	}

	public Actions initDriverActions(WebDriver driver) {
		if (driver == parentDriver) {
			parentAction = new Actions(driver);
			parentWait = new WebDriverWait(driver, 30);
			parentFluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
					.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
			return parentAction;
		} else if (driver == childDriver) {
			childAction = new Actions(driver);
			childWait = new WebDriverWait(driver, 30);
			childFluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
					.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
			return childAction;
		}
		return null;
	}
	
	public Select select(WebDriver driver, By by) {
		WebElement element = findElement(driver, by);
		if (driver == parentDriver) {
			parentSelect = new Select(element);
			return parentSelect;
		} else if (driver == childDriver) {
			childSelect = new Select(element);
			return childSelect;
		}
		return null;
	}

	public void openNewTab() {
		int tabs = 0;
		((JavascriptExecutor) parentDriver).executeScript("window.open()");
		browserTabs = new ArrayList<String>(parentDriver.getWindowHandles());
		tabs = browserTabs.size() - 1;
		// Pass Driver Control to latest opened tab
		parentDriver.switchTo().window(browserTabs.get(tabs));
	}
}
