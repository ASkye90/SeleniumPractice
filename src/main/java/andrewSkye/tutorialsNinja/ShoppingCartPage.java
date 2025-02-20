package andrewSkye.tutorialsNinja;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The Cart Page in Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class ShoppingCartPage extends BaseTNPage {

	@FindBy(css = ".table-responsive tbody tr")
	private List<WebElement> productList;

	private WebDriverWait wait;

	/**
	 * Creates a Shopping Cart Page
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public ShoppingCartPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	/**
	 * Get the name of all products in the shopping cart.
	 * 
	 * @return Names of all products in shopping cart
	 */
	public List<String> getProductsByName() {
		return productList.stream().map(e -> e.findElement(By.cssSelector("td.text-left>a")).getText()).toList();
	}

	/**
	 * Check if there are any warnings displayed
	 * 
	 * @return True if there are any warnings
	 */
	public Boolean hasWarning() {
		List<WebElement> warnings = driver.findElements(By.className("alert-danger"));
		if (warnings.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Get the warning text displayed
	 * 
	 * @return Warning text or "N/A" if none found
	 */
	public String getWarning() {
		if (hasWarning()) {
			return driver.findElement(By.className("alert-danger")).getText();
		}
		return "N/A";
	}

	/**
	 * Get the name of all products that have a warning associated with them.
	 * 
	 * @return Name of all products with a warning
	 */
	public List<String> getProductsWithWarning() {
		// Start with all products annotated with ***
		List<String> productsWithWarning = productList.stream()
				.filter(e -> e.findElements(By.cssSelector("td.text-left>span.text-danger")).size() > 0)
				.map(e -> e.findElement(By.cssSelector("td.text-left>a")).getText()).toList();

		// Add in products with minimum quantity warning
		// ie Minimum order amount for Apple Cinema 30" is 2!
		String warning = getWarning();
		if (warning.contains("Minimum order")) {
			productsWithWarning.add(warning.split("for")[1].split("is")[0].trim());
		}
		return productsWithWarning;
	}

	/**
	 * Remove all instances of products with a given name from shopping cart
	 * 
	 * @param productName Name of product to remove
	 */
	public void removeProduct(String productName) {
		for (WebElement product : productList) {
			if (product.findElement(By.cssSelector("td.text-left>a")).getText().equals(productName)) {
				product.findElement(By.cssSelector("button[data-original-title='Remove']")).click();
				wait.until(ExpectedConditions.stalenessOf(product));
				removeProduct(productName);
				break;
			}
		}
	}

	/**
	 * Navigate to the Checkout Page.
	 * 
	 * @return The Checkout Page
	 */
	public CheckoutPage goToCheckout() {
		header.checkout.click();
		return new CheckoutPage(driver);
	}
}
