package andrewSkye.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class StandAloneTest {

	
	
	@Test
	public void standAloneTest() {
		SoftAssert softAssert = new SoftAssert();
		WebDriver driver = new ChromeDriver();
		//WebDriver driver = new FirefoxDriver();
		//WebDriver driver = new EdgeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get("https://the-internet.herokuapp.com/");

		/*
		 * Checking if the title and header are correct in AB Testing page
		 */
		String expectedHeader = "A/B Test";
		String expectedTitle = "The Internet";

		driver.findElement(By.cssSelector("a[href$='abtest']")).click();
		String pageTitle = driver.getTitle();
		System.out.println(pageTitle);
		softAssert.assertEquals(expectedTitle, pageTitle);

		String pageHeader = driver.findElement(By.tagName("h3")).getText();
		System.out.println(pageHeader);
		softAssert.assertTrue(pageHeader.contains(expectedHeader));

		/*
		 * Flaky test 
		 * Checking if Mario profile picture appears on dynamic content page
		 * within a set amount of refreshes.
		 */
		driver.navigate().back();
		driver.findElement(By.cssSelector("a[href$='dynamic_content']")).click();

		WebElement profilePic = null;
		int refreshes = 0;
		int maxTries = 5;
		do {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("img")));
			List<WebElement> profileImages = driver.findElements(By.tagName("img"));
			profilePic = profileImages.stream().filter(webEle -> webEle.getDomAttribute("src").contains("Avatar-1"))
					.findFirst().orElse(null);
			if (profilePic == null) {
				System.out.println("Refresh");
				refreshes++;
				driver.navigate().refresh();
			}
		} while (profilePic == null && refreshes < maxTries);

		softAssert.assertNotNull(profilePic, "Unable to find Mario within " + maxTries + " retries");
		System.out.println((profilePic != null) ? refreshes + " refresh: " + profilePic.getDomAttribute("src")
				: "Unable to find Mario in " + refreshes + " refresh");

		driver.close();
		softAssert.assertAll();
	}

}
