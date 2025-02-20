package andrewSkye.tests.tutorialsNinja;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import andrewSkye.baseObjects.BaseTest;
import andrewSkye.resources.JSONMapper;
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
	 * @return The Main Page
	 */
	private TNMainPage goToMain() {
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl != url && currentUrl != alternateUrl) {
			driver.get(url);
		}
		return new TNMainPage(driver);
	}

	/**
	 * Test for logging into the website. Checks if the correct user is logged in
	 * after running.
	 * 
	 * @param email    User email
	 * @param password User password
	 * @param context  Test context that is currently running
	 */
	@Parameters({ "email", "pass" })
	@Test
	public void login(String email, String password, ITestContext context) {
		TNMainPage mainPage = goToMain();
		ExtentTest extentTest = createExtentTest("TN - Login", "Logs into the website", context);
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
	 * Test adding a product to the cart. Checks if the product exist and can be
	 * successfully added to cart.
	 * 
	 * @param context Test context that is currently running
	 */
	@Test(dependsOnMethods = "login", dataProvider = "getProductsToAdd")
	public void addProductToCart(HashMap<String, String> productInfo, ITestContext context) {
		String product = productInfo.get("product");
		ExtentTest extentTest = createExtentTest("TN - Add Product to Cart", "Adds a product to the cart.",
				context);
		addProductToCart(productInfo, extentTest);
	}

	/**
	 * Test checking out with a product.
	 * 
	 * Checks if an order can be successfully placed with the product in the
	 * shopping cart.
	 * 
	 * @param context Test context that is currently running
	 */
	@Test(dependsOnMethods = "login", dataProvider = "getProductsToCheckout")
	public void checkoutProduct(HashMap<String, String> productInfo, ITestContext context) {
		String product = productInfo.get("product");
		ExtentTest extentTest = createExtentTest("TN - Checkout Product: " + product, "Checksout with a specific product.",
				context);
		ProductPage productPage = addProductToCart(productInfo, extentTest);
		ConfirmationPage confirmationPage = checkoutProduct(productPage, product, extentTest);
		softAssert.assertEquals(confirmationPage.getConfirmationHeader(), "Your order has been placed!");
	}

	/**
	 * Function to add a product to the cart.
	 * 
	 * @param product    Name of product to add
	 * @param extentTest ExtentTest instance for currently running test
	 * @return Product Page user remains on after adding to cart
	 */
	private ProductPage addProductToCart(HashMap<String, String> productInfo, ExtentTest extentTest) {
		TNMainPage mainPage = goToMain();
		String product = productInfo.get("product");

		extentTest.log(Status.INFO, "Collecting category names");
		List<String> categories = mainPage.getAllMenuCategories();
		BaseTNPage currentPage = mainPage;


		extentTest.log(Status.INFO, "Searching for " + product + " in each category.");
		Boolean productFound = false;
		int index = 0;
		do {
			String category = categories.get(index);
			currentPage = currentPage.goToCategory(category);
			if (((ProductListPage) currentPage).getAllProductNames().contains(product)) {
				extentTest.log(Status.PASS, "Found " + product + " in " + category);
				productFound = true;
				break;
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
		productPage.enterOptions(productInfo);
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
	 * Function to checkout with a given product.
	 * 
	 * @param currentPage Current page the driver is on
	 * @param product     Name of product
	 * @param extentTest  ExtentTest instance for currently running test
	 * @return Confirmation Page user is landed in after checkout
	 */
	private ConfirmationPage checkoutProduct(BaseTNPage currentPage, String product, ExtentTest extentTest) {
		ShoppingCartPage cartPage = currentPage.goToShoppingCart();
		extentTest.log(Status.INFO, "Opened Shopping Cart page.");

		if (cartPage.hasWarning()) {
			List<String> productsWithWarning = cartPage.getProductsWithWarning();
			if (productsWithWarning.contains(product)) {
				extentTest.log(Status.WARNING, cartPage.getWarning());
				Assert.fail(product + " cannot be purchased due to warning.");
			} else {
				extentTest.log(Status.INFO, "Warning present for other item(s): " + cartPage.getWarning());
				for (String pww : productsWithWarning) {
					cartPage.removeProduct(pww);
					extentTest.log(Status.INFO, "Removed all " + pww + " from cart due to warning");
				}
			}
		}

		CheckoutPage checkoutPage = cartPage.goToCheckout();
		extentTest.log(Status.INFO, "Opened Checkout page");
		checkoutPage.fillBillingDetails();
		extentTest.log(Status.INFO, "Filled out billing details");
		checkoutPage.fillDeliveryDetails();
		extentTest.log(Status.INFO, "Filled out delivery details");
		checkoutPage.fillDeliveryMethod();
		extentTest.log(Status.INFO, "Filled out delivery method");
		checkoutPage.fillPaymentMethod();
		extentTest.log(Status.INFO, "Filled out payment method");
		ConfirmationPage confirmationPage = checkoutPage.confirmOrder();
		extentTest.log(Status.INFO, "Confirmed order");
		return confirmationPage;
	}

	/**
	 * Data Provider for addProductsToCart test.
	 * 
	 * @return HashMap at each index containing specific product name and options
	 * @throws FileNotFoundException
	 */
	@DataProvider
	public Object[][] getProductsToAdd() throws FileNotFoundException {
		return getDataFromJson("//src//test//java//andrewSkye//tests//tutorialsNinja//AddProductsToCart.json");
	}

	/**
	 * Data Provider for addProductsToCart test.
	 * 
	 * @return HashMap at each index containing specific product name and options
	 * @throws FileNotFoundException
	 */
	@DataProvider
	public Object[][] getProductsToCheckout() throws FileNotFoundException {
		return getDataFromJson("//src//test//java//andrewSkye//tests//tutorialsNinja//CheckoutWithProducts.json");
	}

	/**
	 * Function to convert JSON file to a DataProvider appropriate Object[][]
	 * holding a HashMap at each index.
	 * 
	 * @param relativeFilePath File path to JSON relative to project root directory
	 * @return HashMap at each index containing specific product name and options
	 * @throws FileNotFoundException
	 */
	private Object[][] getDataFromJson(String relativeFilePath) throws FileNotFoundException {
		List<HashMap<String, String>> listOfProducts = JSONMapper.parseJSON(relativeFilePath);
		Object[][] data = new Object[listOfProducts.size()][1];
		for (int i = 0; i < listOfProducts.size(); i++) {
			Map<String, String> product = listOfProducts.get(i);
			data[i][0] = product;
		}
		return data;
	}
}
