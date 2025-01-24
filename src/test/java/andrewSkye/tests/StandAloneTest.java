package andrewSkye.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class StandAloneTest {

	public static void main(String[] args) {
		
		String expectedHeader = "A/B Test";
		String expectedTitle = "The Internet";
		
		//WebDriver driver = new ChromeDriver();
		//WebDriver driver = new FirefoxDriver();
		WebDriver driver = new EdgeDriver(); 
		driver.manage().window().maximize();
		driver.get("https://the-internet.herokuapp.com/");
		
		driver.findElement(By.cssSelector("a[href$='abtest']")).click();
		
		String pageTitle = driver.getTitle();
		System.out.println(pageTitle);
		Assert.assertEquals(expectedTitle, pageTitle);
		
		String pageHeader = driver.findElement(By.tagName("h3")).getText();
		System.out.println(pageHeader);
		Assert.assertTrue(pageHeader.contains(expectedHeader));
		
		driver.close();
	}

}
