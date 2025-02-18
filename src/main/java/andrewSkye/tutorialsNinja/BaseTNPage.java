package andrewSkye.tutorialsNinja;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import andrewSkye.baseObjects.BasePage;

public class BaseTNPage extends BasePage {

	protected Header header;

	public BaseTNPage(WebDriver driver) {
		super(driver);
		header = new Header(driver);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Logs into the page
	 */
	public AccountPage login(String email, String password) {
		header.myAccount.click();
		header.myAccount.findElement(By.cssSelector("li a[href$='login']")).click();
		LoginPage loginPage = new LoginPage(driver);
		return loginPage.login(email, password);
	}

	/*
	 * Checks if the user is already logged in.
	 */
	public Boolean isLoggedIn() {
		// Using header My Account drop-down to check -- account isn't displayed clearly
		// on page.
		List<WebElement> accountOptions = header.myAccount.findElements(By.cssSelector("li a"));
		for (WebElement option : accountOptions) {
			System.out.println("Option: " + option.getDomAttribute("href"));
			if (option.getDomAttribute("href").endsWith("register")) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Opens up the product catalog for supplied main category from menu.
	 */
	public ProductListPage goToCategory(String category) {
		List<WebElement> menuCategories = header.menu.findElements(By.cssSelector(".nav>li"));
		WebElement categoryItem = menuCategories.stream().filter(e->e.getText().equals(category)).findFirst().get();
		String classAttr = categoryItem.getDomAttribute("class");
		if (classAttr != null && classAttr.contains("dropdown")) {
			categoryItem.click();
			categoryItem.findElement(By.className("see-all")).click();
		} else {
			categoryItem.click();
		}
		return new ProductListPage(driver);
	}

	public CartPage goToCart() {
		header.shoppingCart.click();
		return new CartPage(driver);
	}
	
	public List<String> getAllMenuCategories() {
		List<WebElement> menuCategories = header.menu.findElements(By.cssSelector(".nav>li"));
		return menuCategories.stream().map(WebElement::getText).toList();
	}
}
