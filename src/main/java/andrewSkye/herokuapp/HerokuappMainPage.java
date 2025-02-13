package andrewSkye.herokuapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import andrewSkye.baseObjects.BasePage;

public class HerokuappMainPage extends BasePage {


	public HerokuappMainPage(WebDriver driver) {
		super(driver);
	}

	public ABTestingPage goToABTestingPage() {
		driver.findElement(By.cssSelector("a[href$='abtest']")).click();
		return new ABTestingPage(driver);
	}

	public DynamicContentPage goToDynamicContentPage() {
		driver.findElement(By.cssSelector("a[href$='dynamic_content']")).click();
		return new DynamicContentPage(driver);
	}

	public HoversPage goToHoversPage() {
		driver.findElement(By.cssSelector("a[href$='hovers']")).click();
		return new HoversPage(driver);
	}
}
