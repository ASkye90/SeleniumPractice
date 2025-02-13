package andrewSkye.humanBenchmark;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BasePage;

public class HBMainPage extends BasePage {

	private Actions action;

	@FindBy(css = "a[href$='reactiontime']")
	private WebElement reactionTimeButton;

	@FindBy(css = "a[href$='typing']")
	private WebElement typingButton;

	public HBMainPage(WebDriver driver) {
		super(driver);
		action = new Actions(driver);
	}

	public ReactionTimePage goToReactionTime() {
		action.scrollToElement(reactionTimeButton).moveToElement(reactionTimeButton).click().build().perform();
		return new ReactionTimePage(driver);
	}

	public TypingPage goToTyping() {
		action.scrollToElement(reactionTimeButton).moveToElement(typingButton).click().build().perform();
		return new TypingPage(driver);
	}
}
