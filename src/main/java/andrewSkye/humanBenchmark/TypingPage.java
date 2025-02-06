package andrewSkye.humanBenchmark;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BasePage;

public class TypingPage extends BasePage {

	private String paragraph = "";
	private Actions action;
	
	
	@FindBy(className="letters")
	WebElement playSpace;
	
	@FindBy(className = "incomplete")
	List<WebElement> characters;
	
	@FindBy(css=".css-1qvtbrk h1")
	WebElement fullResult;

	public TypingPage(WebDriver driver) {
		super(driver);
		action = new Actions(driver);
		parseFullParagraph();
		System.out.println(paragraph);
	}

	/*
	 * Parses the paragraph displayed on-screen into the 'paragraph' String value.
	 */
	private void parseFullParagraph() {
		for (WebElement character : characters) {
			String c = character.getText();
			if (c.isBlank()) {
				paragraph += " ";
			} else {
				paragraph += c;
			}
		}
	}
	
	/*
	 * Types the full paragraph into the window.
	 * 
	 * @return	Integer	Words Per Minute given after finishing typing.
	 */
	public Integer typeTest() {
		action.moveToElement(playSpace).click().build().perform();
		action.sendKeys(paragraph).build().perform();
		return Integer.valueOf(fullResult.getText().split("wpm")[0]);
	}
}
