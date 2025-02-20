package andrewSkye.tutorialsNinja;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Order Confirmation Page for the Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class ConfirmationPage extends BaseTNPage {

	@FindBy(css="#content h1")
	private WebElement orderPlacedText;
	
	/**
	 * Creates a Confirmation Page.
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Gets the header text for confirmation message.
	 * 
	 * @return	Header text for confirmation message
	 */
	public String getConfirmationHeader() {
		return orderPlacedText.getText();
	}
}
