package andrewSkye.tutorialsNinja;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Account Page for the Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class AccountPage extends BaseTNPage {

	@FindBy(css="#column-right a[href$='edit']")
	private WebElement editAccount;
	
	/**
	 * Creates an Account Page.
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public AccountPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Gets the user name for currently logged in user.
	 * 
	 * @return	The user name of currently logged in user.
	 */
	public String getUserEmail() {
		editAccount.click();
		return driver.findElement(By.id("input-email")).getText();
	}
}
