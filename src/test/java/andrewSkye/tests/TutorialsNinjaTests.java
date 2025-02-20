package andrewSkye.tests;

import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import andrewSkye.baseObjects.BaseTest;
import andrewSkye.tutorialsNinja.AccountPage;
import andrewSkye.tutorialsNinja.BaseTNPage;
import andrewSkye.tutorialsNinja.ShoppingCartPage;
import andrewSkye.tutorialsNinja.CheckoutPage;
import andrewSkye.tutorialsNinja.ConfirmationPage;
import andrewSkye.tutorialsNinja.ProductListPage;
import andrewSkye.tutorialsNinja.ProductPage;
import andrewSkye.tutorialsNinja.TNMainPage;

/**
 * Tests for Tutorials Ninja demo website.
 * 
 * @author Andrew Skye
 */
public class TutorialsNinjaTests extends BaseTest {

	private String url = "https://tutorialsninja.com/demo/";
	private String alternateUrl = "https://tutorialsninja.com/demo/index.php?route=common/home";

	/**
	 * Load into the Main Page.
	 * 
	 * @return	The Main Page
	 */
	private TNMainPage goToMain() {
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl != url && currentUrl != alternateUrl) {
			driver.get(url);
		}
		return new TNMainPage(driver);
	}

	/**
	 * Test for logging into the website.
	 * Checks if the correct user is logged in after running.
	 * 
	 * @param email		User email
	 * @param password		User password
	 * @param context 	Test context that is currently running
	 */
	@Parameters({ "email", "pass" })
	@Test
	public void login(String email, String password, ITestContext context) {
		TNMainPage mainPage = goToMain();
		ExtentTest extentTest = createExtentTest("Login", "Logs into the website", context);
		extentTest.log(Status.INFO, "Logging in");
		
		AccountPage accountPage;
		if (!mainPage.isLoggedIn()) {
			accountPage = mainPage.login(email, password);
			extentTest.log(Status.INFO, "Login complete");
		} else {
			accountPage = mainPage.goToMyAccount();
			extentTest.log(Status.WARNING, "A user appears to already be logged in.");
		}
		
		String userEmail = accountPage.getUserEmail();
		softAssert.assertEquals(userEmail, email);
	}

	/**
	 * Test adding a product to the cart.
	 * Checks if the product exist and can be successfully added to cart.
	 * 
	 * @param context 	Test context that is currently running
	 */
	@Test(dependsOnMethods = "login")
	public void addProductToCart(ITestContext context) {
		String product = "Apple Cinema 30\"";
		ExtentTest extentTest = createExtentTest("Add Product to Cart: " + product,
				"Adds a specific hard-coded (for now) product to cart.", context);
		extentTest.log(Status.INFO, "Collecting category names");
		addProductToCart(product, context, extentTest);
	}

	/**
	 * Adds a product to the cart.
	 * 
	 * @param 	product		Name of product to add
	 * @param 	context 	Test context that is currently running
	 * @param 	extentTest	ExtentTest instance for currently running test
	 * @return	Product Page user remains on after adding to cart
	 */
	private ProductPage addProductToCart(String product, ITestContext context, ExtentTest extentTest){
		TNMainPage mainPage = goToMain();
		List<String> categories = mainPage.getAllMenuCategories();
		BaseTNPage currentPage = mainPage;

		Boolean productFound = false;
		int index = 0;
		do {
			String category = categories.get(index);
			extentTest.log(Status.INFO, "Going through: " + category);
			currentPage = currentPage.goToCategory(category);
			if (((ProductListPage) currentPage).getAllProductNames().contains(product)) {
				extentTest.log(Status.PASS, "Found " + product + " in " + category);
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
		ProductListPage productListPage = (ProductListPage) currentPage;
		ProductPage productPage = productListPage.goToProduct(product);
		extentTest.log(Status.INFO, "Opened " + product + " product page.");
		extentTest.log(Status.INFO, "Entering available options");
		productPage.enterOptions(new HashMap<String, String>());
		extentTest.log(Status.INFO, "Adding to Cart");
		String addToCartLog = productPage.clickAddToCart();
		if (addToCartLog.contains("Success")) {
			extentTest.log(Status.PASS, addToCartLog);
		} else {
			Assert.fail(addToCartLog);
		}
		return productPage;
	}

	/**
	 * Test checking out with a product.
	 * 
	 * Checks if an order can be successfully placed with the product in the shopping cart.
	 * 
	 * @param context 	Test context that is currently running
	 */
	@Test(dependsOnMethods = "login")
	public void checkoutProduct(ITestContext context) {
		String product = "HP LP3065";
		ExtentTest extentTest = createExtentTest("Checkout Product: " + product,
				"Checksout with a specific hard-coded (for now) product.", context);
		extentTest.log(Status.INFO, "Collecting category names");
		ProductPage productPage = addProductToCart(product, context, extentTest);
		ConfirmationPage confirmationPage = checkoutProduct(productPage, product, context, extentTest);
		softAssert.assertEquals(confirmationPage.getConfirmationHeader(),"Your order has been placed!");
	}

	/**
	 * Checkout with a given product.
	 * 
	 * @param 	currentPage	Current page the driver is on
	 * @param 	product		Name of product
	 * @param 	context 	Test context that is currently running
	 * @param 	extentTest	ExtentTest instance for currently running test
	 * @return	Confirmation Page user is landed in after checkout
	 */
	private ConfirmationPage checkoutProduct(BaseTNPage currentPage, String product, ITestContext context, ExtentTest extentTest) {
		ShoppingCartPage cartPage = currentPage.goToShoppingCart();
		if (cartPage.hasWarning()) {
			List<String> productsWithWarning = cartPage.getProductsWithWarning();
			if (productsWithWarning.contains(product)) {
				extentTest.log(Status.WARNING, cartPage.getWarning());
				Assert.fail(product + "cannot be purchased due to warning.");
			} else {
				for (String pww : productsWithWarning) {
					cartPage.removeProduct(pww);
				}
			}
		}

		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.fillBillingDetails();
		checkoutPage.fillDeliveryDetails();
		checkoutPage.fillDeliveryMethod();
		checkoutPage.fillPaymentMethod();
		return checkoutPage.confirmOrder();
	}
}
