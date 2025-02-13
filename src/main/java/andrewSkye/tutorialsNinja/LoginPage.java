package andrewSkye.tutorialsNinja;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BaseTNPage {

	@FindBy(id = "input-email")
	private WebElement emailInput;

	@FindBy(id = "input-password")
	private WebElement passInput;

	@FindBy(css = "input[value='Login']")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public AccountPage login(String email, String pass) {
		emailInput.sendKeys(email);
		passInput.sendKeys(pass);
		loginButton.click();
		return new AccountPage(driver);

	}

}
