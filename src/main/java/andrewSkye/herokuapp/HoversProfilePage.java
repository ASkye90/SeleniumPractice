package andrewSkye.herokuapp;

import org.openqa.selenium.WebDriver;

import andrewSkye.baseObjects.BasePage;

/**
 * The Hovers Profile Page for The-Internet Herokuapp website.
 * 
 * @author Andrew Skye
 */
public class HoversProfilePage extends BasePage {

	/**
	 * Creates a Hovers Profile Page.
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public HoversProfilePage(WebDriver driver) {
		super(driver);
	}

}
