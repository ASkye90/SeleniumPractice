package andrewSkye.baseObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

public class BaseTest {

	protected WebDriver driver;
	protected SoftAssert softAssert;
	
	
	@BeforeTest
	public void beforeTest() {	
		softAssert = new SoftAssert();
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
