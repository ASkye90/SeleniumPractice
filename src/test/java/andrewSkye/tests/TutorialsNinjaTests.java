package andrewSkye.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import andrewSkye.baseObjects.BaseTest;
import andrewSkye.tutorialsNinja.BaseTNPage;
import andrewSkye.tutorialsNinja.ProductListPage;
import andrewSkye.tutorialsNinja.ProductPage;
import andrewSkye.tutorialsNinja.TNMainPage;

public class TutorialsNinjaTests extends BaseTest {

	private String url = "https://tutorialsninja.com/demo/";
	private String alternateUrl = "https://tutorialsninja.com/demo/index.php?route=common/home";

	private TNMainPage goToMain() {
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl != url && currentUrl != alternateUrl) {
			driver.get(url);
		}
		return new TNMainPage(driver);
	}

	@Parameters({ "email", "pass" })
	@Test
	public void login(String email, String password, ITestContext context) {
		TNMainPage mainPage = goToMain();
		ExtentTest extentTest = createExtentTest("Login", "Logs into the website", context);
		extentTest.log(Status.INFO, "Logging in");
		if (!mainPage.isLoggedIn()) {
			mainPage.login(email, password);
			extentTest.log(Status.INFO, "Login complete");
		} else {
			extentTest.log(Status.FAIL, "A user appears to already be logged in.");
		}
	}

	@Test(dependsOnMethods = "login")
	public void doSomething(ITestContext context) {
		TNMainPage mainPage = goToMain();
		ExtentTest extentTest = createExtentTest("Sommething", "Does something after login", context);
		extentTest.log(Status.WARNING, "We did it!");
		extentTest.log(Status.INFO, mainPage.getUrl());
	}

	@Test
	public void addProductToCart(ITestContext context) throws IOException {
		TNMainPage mainPage = goToMain();
		ExtentTest extentTest = createExtentTest("Add Product to Cart", "Adds a specific hard-coded (for now) product to cart.", context);
		extentTest.log(Status.INFO, "Collecting category names");
		
		String product="Apple Cinema 30\"";
		
		List<String> categories = mainPage.getAllMenuCategories();
		BaseTNPage currentPage = mainPage;
		
		Boolean productFound = false;
		int index = 0;
		do {
			String category = categories.get(index);
			extentTest.log(Status.INFO, "Going through: " + category);
			currentPage = currentPage.goToCategory(category);
			if(((ProductListPage)currentPage).getAllProductNames().contains(product)) {
				extentTest.log(Status.PASS,"Found " + product + " in " + category);
				productFound = true;
				break;
			} else {
				extentTest.log(Status.INFO, product + " not in " + category);
			}
			index++;
		} while (!productFound && index < categories.size());
		if (!productFound) {
			Assert.fail(product + " not found in any categories.");
		}
		ProductListPage productListPage = (ProductListPage)currentPage;
		ProductPage productPage = productListPage.goToProduct(product);
		extentTest.log(Status.INFO, "Opened " + product + " product page.");
		extentTest.log(Status.INFO,"Entering available options");
		productPage.enterOptions(new HashMap<String,String>());
		extentTest.log(Status.INFO, "Adding to Cart");
		String addToCartLog = productPage.clickAddToCart();
		if (addToCartLog.contains("Success")) {
			extentTest.log(Status.PASS, addToCartLog);
		} else {
			extentTest.log(Status.FAIL, addToCartLog);
		}
	}

}
