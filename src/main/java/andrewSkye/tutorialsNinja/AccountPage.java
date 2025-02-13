package andrewSkye.tutorialsNinja;

import org.openqa.selenium.WebDriver;


public class AccountPage extends BaseTNPage {

	public AccountPage(WebDriver driver) {
		super(driver);
		header = new Header(driver);
	}
}
