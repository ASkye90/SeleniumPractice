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
	
	/** My Account drop-down */
	@FindBy(css="#top-links li:has(a[title='My Account'])")
	public WebElement myAccount;
	
	/** Wish List link */
	@FindBy(id="wishlist-total")
	public WebElement wishlist;
	
	/** Shopping Cart link */
	@FindBy(css="#top-links a[title='Shopping Cart']")
	public WebElement shoppingCart;
	
	/** Checkout link */
	@FindBy(css="#top-links a[title='Checkout']")
	public WebElement checkout;
	
	/** Currency drop-down */
	@FindBy(id="form-currency")
	public WebElement currency;
	
	/** Search group */
	@FindBy(id="search")
	public WebElement search;
	
	/** Cart group */
	@FindBy(id="cart")
	public WebElement cart;
	
	/** Category navigation menu */
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
