package andrewSkye.tutorialsNinja;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BaseTNPage {

	@FindBy(css = ".table-responsive tbody tr")
	private List<WebElement> productList;

	@FindBy(className = "alert-danger")
	private WebElement warning;

	private WebDriverWait wait;

	public CartPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	public List<String> getProductsByName() {
		return productList.stream().map(e -> e.findElement(By.cssSelector("td.text-left>a")).getText()).toList();
	}

	public Boolean hasWarning() {
		if (warning != null) {
			return true;
		}
		return false;
	}

	public String getWarning() {
		if (hasWarning()) {
			return warning.getText();
		}
		return "N/A";
	}

	public List<String> getProductsWithWarning() {
		return productList.stream()
				.filter(e -> e.findElements(By.cssSelector("td.text-left>span.text-danger")).size() > 0)
				.map(e -> e.findElement(By.cssSelector("td.text-left>a")).getText()).toList();
	}

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

	public CheckoutPage goToCheckout() {
		header.checkout.click();
		return new CheckoutPage(driver);
	}
}
