package andrewSkye.tutorialsNinja;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import andrewSkye.baseObjects.BasePage;

/**
 * The Base Page for the Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class BaseTNPage extends BasePage {

	protected Header header;

	/**
	 * Creates a Base TN Page with access to Header.
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public BaseTNPage(WebDriver driver) {
		super(driver);
		header = new Header(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Logs into the website.
	 * 
	 * @param email    Email to use for login
	 * @param password Password to use for login
	 * @return Account Page that user lands in after logging in.
	 */
	public AccountPage login(String email, String password) {
		header.myAccount.click();
		header.myAccount.findElement(By.cssSelector("li a[href$='login']")).click();
		LoginPage loginPage = new LoginPage(driver);
		return loginPage.login(email, password);
	}

	/**
	 * Checks if the user is already logged in.
	 * 
	 * @return True if user is logged in.
	 */
	public Boolean isLoggedIn() {
		// Using My Account drop-down in header to check,
		// account isn't displayed clearly on page.
		List<WebElement> accountOptions = header.myAccount.findElements(By.cssSelector("li a"));
		for (WebElement option : accountOptions) {
			if (option.getDomAttribute("href").endsWith("register")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Navigate to My Account Page.
	 * 
	 * @return	The Account Page
	 */
	public AccountPage goToMyAccount() {
		header.myAccount.click();
		header.myAccount.findElement(By.cssSelector("li a[href$='account']")).click();
		return new AccountPage(driver);
	}
	
	/**
	 * Navigates to the product catalog for category from menu.
	 * 
	 * @param category Name of category
	 * @return The Product List Page the driver lands in
	 */
	public ProductListPage goToCategory(String category) {
		List<WebElement> menuCategories = header.menu.findElements(By.cssSelector(".nav>li"));
		WebElement categoryItem = menuCategories.stream().filter(e -> e.getText().equals(category)).findFirst().get();
		String classAttr = categoryItem.getDomAttribute("class");
		if (classAttr != null && classAttr.contains("dropdown")) {
			categoryItem.click();
			categoryItem.findElement(By.className("see-all")).click();
		} else {
			categoryItem.click();
		}
		return new ProductListPage(driver);
	}

	/**
	 * Navigate to the Shopping Cart Page.
	 * 
	 * @return The Shopping Cart Page.
	 */
	public ShoppingCartPage goToShoppingCart() {
		header.shoppingCart.click();
		return new ShoppingCartPage(driver);
	}

	/**
	 * Get the display name for all Product categories in header
	 * 
	 * @return	Name of all Product categories
	 */
	public List<String> getAllMenuCategories() {
		List<WebElement> menuCategories = header.menu.findElements(By.cssSelector(".nav>li"));
		return menuCategories.stream().map(WebElement::getText).toList();
	}
}
