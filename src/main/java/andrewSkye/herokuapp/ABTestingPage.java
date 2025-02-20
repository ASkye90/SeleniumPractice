package andrewSkye.herokuapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BasePage;

/**
 * The AB Testing Page for The-Internet Herokuapp website.
 * 
 * @author Andrew Skye
 */
public class ABTestingPage extends BasePage {

	/**
	 * Creates an AB Testing Page.
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public ABTestingPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(tagName = "h3")
	private WebElement pageHeader;

	@FindBy(tagName = "p")
	private WebElement paragraphText;

	/**
	 * Get the header of the page
	 * 
	 * @return The header of the page.
	 */
	public String getHeader() {
		return pageHeader.getText();
	}

	/**
	 * Get the text block on the page
	 * 
	 * @return The text block on the page.
	 */
	public String getText() {
		return paragraphText.getText();
	}
}
