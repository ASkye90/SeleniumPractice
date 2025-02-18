package andrewSkye.tutorialsNinja;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BaseTNPage {

	@FindBy(css="#accordion .panel")
	private List<WebElement> checkoutPanels;
	
	private WebDriverWait wait;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		System.out.println(checkoutPanels.stream().map(e->e.findElement(By.className("panel-title")).getText()).toList().toString());
	}

	public void fillBillingDetails() {
		WebElement billingDetails = getPanel("Billing Details");
		billingDetails.findElement(By.cssSelector("input[type='button']")).click();
	}
	
	public void fillDeliveryDetails() {
		WebElement deliveryDetails = getPanel("Delivery Details");
		deliveryDetails.findElement(By.cssSelector("input[type='button']")).click();
	}
	
	public void fillDeliveryMethod() {
		WebElement deliveryMethod = getPanel("Delivery Method");
		deliveryMethod.findElement(By.cssSelector("input[type='button']")).click();
	}
	
	public void fillPaymentMethod() {
		WebElement paymentMethod = getPanel("Payment Method");
		paymentMethod.findElement(By.cssSelector("input[type='checkbox']")).click();
		paymentMethod.findElement(By.cssSelector("input[type='button']")).click();
	}
	
	public void confirmOrder() {
		WebElement confirmOrder = getPanel("Confirm Order");
		confirmOrder.findElement(By.cssSelector("input[type='button']")).click();
	}
	
	
	private WebElement getPanel(String panelTitle) {
		WebElement panel = checkoutPanels.stream().filter(e->e.findElement(By.className("panel-title")).getText().contains(panelTitle)).findFirst().get();
		wait.until(ExpectedConditions.visibilityOf(panel));
		return panel;
	}
}
