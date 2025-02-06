package andrewSkye.baseObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

	protected WebDriver driver;
		
	public BasePage(WebDriver driver) {
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		PageFactory.initElements(driver, this);
	}

	public String getTitle() {
		return driver.getTitle();
	}
	
	public String getUrl() {
		return driver.getCurrentUrl();
	}
}
