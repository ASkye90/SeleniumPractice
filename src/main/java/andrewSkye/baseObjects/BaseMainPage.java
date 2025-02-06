package andrewSkye.baseObjects;

import org.openqa.selenium.WebDriver;

public class BaseMainPage extends BasePage {

	protected String url;

	public BaseMainPage(WebDriver driver) {
		super(driver);
	}

	public void goToMainPage() {
		if (driver.getCurrentUrl() != url) {
			driver.get(url);
		}
	}

}
