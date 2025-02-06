package andrewSkye.herokuapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BaseMainPage;
import andrewSkye.baseObjects.BasePage;

public class HerokuappMainPage extends BaseMainPage {
	
	public HerokuappMainPage(WebDriver driver) {
		super(driver);
		url = "https://the-internet.herokuapp.com/";
	}
	
	public ABTestingPage goToABTestingPage() {
		goToMainPage();
		driver.findElement(By.cssSelector("a[href$='abtest']")).click();
		return new ABTestingPage(driver);
	}
	
	
	public DynamicContentPage goToDynamicContentPage() {
		goToMainPage();
		driver.findElement(By.cssSelector("a[href$='dynamic_content']")).click();
		return new DynamicContentPage(driver);
	}
	
	public HoversPage goToHoversPage() {
		goToMainPage();
		driver.findElement(By.cssSelector("a[href$='hovers']")).click();
		return new HoversPage(driver);
	}
}
