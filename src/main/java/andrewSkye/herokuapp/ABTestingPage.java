package andrewSkye.herokuapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BasePage;

public class ABTestingPage extends BasePage {

	public ABTestingPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(tagName = "h3")
	private WebElement pageHeader;

	@FindBy(tagName = "p")
	private WebElement paragraphText;

	/*
	 * @return Header of page
	 */
	public String getHeader() {
		return pageHeader.getText();
	}

	/*
	 * @return Text block on page
	 */
	public String getText() {
		return paragraphText.getText();
	}
}
