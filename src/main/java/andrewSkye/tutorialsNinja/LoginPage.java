package andrewSkye.tutorialsNinja;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Login Page in Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class LoginPage extends BaseTNPage {

	@FindBy(id = "input-email")
	private WebElement emailInput;

	@FindBy(id = "input-password")
	private WebElement passInput;

	@FindBy(css = "input[value='Login']")
	private WebElement loginButton;
	
	/**
	 * Creates a Login Page
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Log into the website with given credentials
	 * 
	 * @param	email	User email
	 * @param	pass	User password
	 * @return	Account Page user lands in after logging in.
	 */
	public AccountPage login(String email, String pass) {
		emailInput.sendKeys(email);
		passInput.sendKeys(pass);
		loginButton.click();
		return new AccountPage(driver);

	}

}
