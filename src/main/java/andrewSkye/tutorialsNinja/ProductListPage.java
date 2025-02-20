package andrewSkye.tutorialsNinja;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * The Product List Page in Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class ProductListPage extends BaseTNPage{

	@FindBy(className="product-layout")
	private List<WebElement> productCards;
	
	private Actions action;
	
	/**
	 * Creates a Product List Page
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public ProductListPage(WebDriver driver) {
		super(driver);
		action = new Actions(driver);
	}

	/**
	 * Get the name of all products listed on the page.
	 * 
	 * @return	List containing all product names
	 */
	public List<String> getAllProductNames() {
		return productCards.stream().map(e->e.findElement(By.cssSelector(".caption a")).getText()).toList();
	}
	
	
	/**
	 * Click on a product link to open up its page
	 * 
	 * @param productName	Name of product
	 * @return	Product Page user is expected to land on.
	 */
	public ProductPage goToProduct(String productName) {
		WebElement productCard = productCards.stream().filter(e->e.findElement(By.cssSelector(".caption a")).getText().contains(productName)).findFirst().get();
		WebElement productLink = productCard.findElement(By.cssSelector(".caption a"));
		action.scrollToElement(productLink);
		productLink.click();
		return new ProductPage(driver);
	}
	
}
