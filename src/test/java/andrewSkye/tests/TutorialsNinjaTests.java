package andrewSkye.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import andrewSkye.baseObjects.BaseTest;
import andrewSkye.tutorialsNinja.BaseTNPage;
import andrewSkye.tutorialsNinja.ProductListPage;
import andrewSkye.tutorialsNinja.TNMainPage;

public class TutorialsNinjaTests extends BaseTest {

	private String url = "https://tutorialsninja.com/demo/";
	private String alternateUrl = "https://tutorialsninja.com/demo/index.php?route=common/home";

	private TNMainPage goToMain() {
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl != url && currentUrl !=  alternateUrl) {
			driver.get(url);
		}
		return new TNMainPage(driver);
	}
	
	@Parameters({"email","pass"})
	@Test
	public void login(String email, String password, ITestContext context) {
		TNMainPage mainPage = goToMain();
		ExtentTest extentTest = createExtentTest("Login", "Logs into the website", context);
		extentTest.log(Status.INFO, "Logging in");
		if (!mainPage.isLoggedIn()) {
			mainPage.login(email, password);
			extentTest.log(Status.INFO,"Login complete");
		} else {
			extentTest.log(Status.FAIL, "A user appears to already be logged in.");
		}
	}
	
	@Test(dependsOnMethods = "login")
	public void doSomething(ITestContext context) {
		TNMainPage mainPage = goToMain();
		ExtentTest extentTest = createExtentTest("Sommething","Does something after login",context);
		extentTest.log(Status.WARNING, "We did it!");
		extentTest.log(Status.INFO,mainPage.getUrl());
	}

	
	@Test
	public void standAlone(ITestContext context) {
		TNMainPage mainPage = goToMain();
		ExtentTest extentTest = createExtentTest("Standalone","Does something for us",context);
		extentTest.log(Status.INFO, "We're starting");
		List<String> categories = mainPage.getAllMainProductCategories();
		extentTest.log(Status.INFO, "Got them all! " + categories);
		BaseTNPage currentPage = mainPage;
		for(String category: categories) {
			extentTest.log(Status.INFO, "Going through: " + category);
			currentPage.goToProducts(category);
			extentTest.log(Status.INFO,"We're in!");
		}
		
	}

}
