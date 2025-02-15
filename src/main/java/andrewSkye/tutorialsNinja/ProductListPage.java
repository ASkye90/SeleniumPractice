package andrewSkye.tutorialsNinja;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class ProductListPage extends BaseTNPage{

	@FindBy(className="product-layout")
	private List<WebElement> productCards;
	
	private Actions action;
	
	public ProductListPage(WebDriver driver) {
		super(driver);
		action = new Actions(driver);
	}

	public List<String> getAllProductNames() {
		return productCards.stream().map(e->e.findElement(By.cssSelector(".caption a")).getText()).toList();
	}
	
	public ProductPage goToProduct(String productName) throws IOException {
		WebElement productCard = productCards.stream().filter(e->e.findElement(By.cssSelector(".caption a")).getText().contains(productName)).findFirst().get();
		WebElement productLink = productCard.findElement(By.cssSelector(".caption a"));
		action.scrollToElement(productLink);
		productLink.click();
		return new ProductPage(driver);
	}
	
}
