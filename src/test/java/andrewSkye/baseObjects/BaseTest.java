package andrewSkye.baseObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {

	protected WebDriver driver;
	
	
	@BeforeTest
	public void beforeTest() {	
		driver = new ChromeDriver();
		
//		WebDriver driver = new FirefoxDriver();
//		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
	}
	
	
	@AfterTest
	public void afterTest() {
		driver.close();
	}
}
