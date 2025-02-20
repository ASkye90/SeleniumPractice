package andrewSkye.tutorialsNinja;

import org.openqa.selenium.WebDriver;

/**
 * The Main Page in Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class TNMainPage extends BaseTNPage {

	/**
	 * Creates a Main Page 
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public TNMainPage(WebDriver driver) {
		super(driver);
	}
}
