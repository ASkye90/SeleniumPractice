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
 * The Checkout Page in Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class CheckoutPage extends BaseTNPage {

	@FindBy(css="#accordion .panel")
	private List<WebElement> checkoutPanels;
	
	private WebDriverWait wait;
	
	/**
	 * Creates a Checkout Page
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public CheckoutPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver,Duration.ofSeconds(5));
	}

	/**
	 * Clicks continue on the billings detail panel
	 */
	public void fillBillingDetails() {
		WebElement billingDetails = getPanelByTitle("Billing Details");
		clickContinue(billingDetails);
	}
	
	/**
	 * Clicks continue on the delivery detail panel
	 */
	public void fillDeliveryDetails() {
		WebElement deliveryDetails = getPanelByTitle("Delivery Details");
		clickContinue(deliveryDetails);
	}
	
	/**
	 * Clicks continue on the delivery method panel
	 */
	public void fillDeliveryMethod() {
		WebElement deliveryMethod = getPanelByTitle("Delivery Method");
		clickContinue(deliveryMethod);
	}
	
	/**
	 * Checks terms and conditions check box, then clicks continue on the payment method panel
	 */
	public void fillPaymentMethod() {
		WebElement paymentMethod = getPanelByTitle("Payment Method");
		WebElement button = paymentMethod.findElement(By.cssSelector("input[type='button']"));
		wait.until(ExpectedConditions.elementToBeClickable(button));
		paymentMethod.findElement(By.cssSelector("input[type='checkbox']")).click();
		button.click();
	}
	
	/**
	 * Clicks continue on the confirm order panel
	 * 
	 * @return	Confirmation Page user lands in
	 */
	public ConfirmationPage confirmOrder() {
		WebElement confirmOrder = getPanelByTitle("Confirm Order");
		clickContinue(confirmOrder);
		return new ConfirmationPage(driver);
	}
	
	/**
	 * Get the WebElement for the specified panel by title
	 * 
	 * @param panelTitle	Title of panel to get
	 * @return	WebElement for the panel
	 */
	private WebElement getPanelByTitle(String panelTitle) {
		WebElement panel = checkoutPanels.stream().filter(e->e.findElement(By.className("panel-title")).getText().contains(panelTitle)).findFirst().get();
		wait.until(ExpectedConditions.visibilityOf(panel));
		return panel;
	}
	
	/**
	 * Clicks continue button on the given panel.
	 * 
	 * @param panel		Given panel
	 */
	private void clickContinue(WebElement panel) {
		WebElement button = panel.findElement(By.cssSelector("input[type='button']"));
		wait.until(ExpectedConditions.elementToBeClickable(button));
		button.click();
	}
}
