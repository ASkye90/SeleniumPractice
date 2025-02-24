package andrewSkye.humanBenchmark;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BasePage;

/**
 * The Main Page for the Human Benchmark website.
 * 
 * @author Andrew Skye
 */
public class HBMainPage extends BasePage {

	private Actions action;

	@FindBy(css = "a[href$='reactiontime']")
	private WebElement reactionTimeButton;

	@FindBy(css = "a[href$='typing']")
	private WebElement typingButton;

	/**
	 * Creates a Main Human Benchmark Page.
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public HBMainPage(WebDriver driver) {
		super(driver);
		action = new Actions(driver);
	}

	/**
	 * Navigate to the Reaction Time Page.
	 * 
	 * @return The Reaction Time Page.
	 */
	public ReactionTimePage goToReactionTime() {
		action.scrollToElement(reactionTimeButton).moveToElement(reactionTimeButton).click().build().perform();
		return new ReactionTimePage(driver);
	}

	/**
	 * Navigate to the Typing Page.
	 * 
	 * @return The Typing Page.
	 */
	public TypingPage goToTypingPage() {
		action.scrollToElement(reactionTimeButton).moveToElement(typingButton).click().build().perform();
		return new TypingPage(driver);
	}
}
