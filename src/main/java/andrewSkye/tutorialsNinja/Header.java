package andrewSkye.tutorialsNinja;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BasePage;

/**
 * The Header in Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class Header extends BasePage {
	
	@FindBy(css="#top-links li:has(a[title='My Account'])")
	public WebElement myAccount;
	
	@FindBy(id="wishlist-total")
	public WebElement wishlist;
	
	@FindBy(css="#top-links a[title='Shopping Cart']")
	public WebElement shoppingCart;
	
	@FindBy(css="#top-links a[title='Checkout']")
	public WebElement checkout;
	
	@FindBy(id="form-currency")
	public WebElement currency;
	
	@FindBy(id="search")
	public WebElement search;
	
	@FindBy(id="cart")
	public WebElement cart;
	
	@FindBy(id="menu")
	public WebElement menu;
	
	/**
	 * Creates a Header
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public Header(WebDriver driver) {
		super(driver);
	}
}
