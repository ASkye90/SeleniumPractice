package andrewSkye.herokuapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import andrewSkye.baseObjects.BasePage;

public class HerokuappMainPage extends BasePage {

	private String url = "https://the-internet.herokuapp.com/";
	
	public HerokuappMainPage(WebDriver driver) {
		super(driver);
	}
	
	public void goToMainPage() {
		if (driver.getCurrentUrl() != url) {
			driver.get(url);
		}
	}
	
	public ABTestingPage goToABTestingPage() {
		goToMainPage();
		ABTestingPage newPage = new ABTestingPage(driver);
		driver.findElement(By.cssSelector("a[href$='abtest']")).click();
		return newPage;
	}
	
	
	public DynamicContentPage goToDynamicContentPage() {
		goToMainPage();
		DynamicContentPage newPage = new DynamicContentPage(driver);
		driver.findElement(By.cssSelector("a[href$='dynamic_content']")).click();
		return newPage;
	}
}
