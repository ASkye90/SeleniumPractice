package andrewSkye.humanBenchmark;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BasePage;

/**
 * The Typing Page for the Human Benchmark website.
 * 
 * @author Andrew Skye
 */
public class TypingPage extends BasePage {

	private Actions action;

	@FindBy(className = "letters")
	private WebElement playSpace;

	@FindBy(className = "incomplete")
	private List<WebElement> characters;

	@FindBy(css = ".css-1qvtbrk h1")
	private WebElement fullResult;

	/**
	 * Creates a Typing Page.
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public TypingPage(WebDriver driver) {
		super(driver);
		action = new Actions(driver);
	}

	/**
	 * Parses the paragraph displayed on-screen and returns it
	 * 
	 * @return	Paragraph displayed
	 */
	public String getFullParagraph() {
		String paragraph = "";
		for (WebElement character : characters) {
			String c = character.getText();
			if (c.isBlank()) {
				paragraph += " ";
			} else {
				paragraph += c;
			}
		}
		return paragraph;
	}

	/**
	 * Types the given paragraph into the window.
	 * 
	 * @param	paragraph	Paragraph to type
	 * 
	 * @return	Integer		Words Per Minute given after finishing typing.
	 */
	public Integer typeTest(String paragraph) {
		action.moveToElement(playSpace).click().build().perform();
		action.sendKeys(paragraph).build().perform();
		return Integer.valueOf(fullResult.getText().split("wpm")[0]);
	}
}
