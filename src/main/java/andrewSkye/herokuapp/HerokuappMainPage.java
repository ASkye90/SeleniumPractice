package andrewSkye.herokuapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import andrewSkye.baseObjects.BasePage;

/**
 * The Main Page for The-Internet Herokuapp website.
 * 
 * @author Andrew Skye
 */
public class HerokuappMainPage extends BasePage {

	/**
	 * Creates a Main Herokuapp Page.
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public HerokuappMainPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Navigate to the AB Testing Page.
	 * 
	 * @return The AB Testing Page.
	 */
	public ABTestingPage goToABTestingPage() {
		driver.findElement(By.cssSelector("a[href$='abtest']")).click();
		return new ABTestingPage(driver);
	}
	
	/**
	 * Navigate to the Dynamic Content Page.
	 * 
	 * @return The Dynamic Content Page.
	 */
	public DynamicContentPage goToDynamicContentPage() {
		driver.findElement(By.cssSelector("a[href$='dynamic_content']")).click();
		return new DynamicContentPage(driver);
	}

	/**
	 * Navigate to the Hovers Page.
	 * 
	 * @return The Hovers Page.
	 */
	public HoversPage goToHoversPage() {
		driver.findElement(By.cssSelector("a[href$='hovers']")).click();
		return new HoversPage(driver);
	}
}
