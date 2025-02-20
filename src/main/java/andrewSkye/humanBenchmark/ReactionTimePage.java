package andrewSkye.humanBenchmark;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import andrewSkye.baseObjects.BasePage;

/**
 * The Reaction Time Page for the Human Benchmark website.
 * 
 * @author Andrew Skye
 */
public class ReactionTimePage extends BasePage {

	private WebDriverWait wait;
	private Actions action;
	private int turn;
	private int[] scores;
	public static int MAX_TURNS = 5;

	@FindBy(className = "css-42wpoy")
	private WebElement playSpace;

	@FindBy(className = "view-splash")
	private WebElement startShowing;

	@FindBy(className = "view-waiting")
	private WebElement waitShowing;

	@FindBy(className = "view-go")
	private WebElement clickShowing;

	@FindBy(className = "view-result")
	private WebElement resultShowing;

	@FindBy(className = "view-scold")
	private WebElement failedShowing;

	@FindBy(className = "view-score")
	private WebElement scoreShowing;

	@FindBy(css = ".css-1qvtbrk div")
	private WebElement individualResult;

	@FindBy(css = ".css-1qvtbrk h1")
	private WebElement fullResult;

	/**
	 * Creates a Reaction Time Page.
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public ReactionTimePage(WebDriver driver) {
		super(driver);
		action = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.pollingEvery(Duration.ofMillis(50));
		turn = 0;
		scores = new int[5];
	}

	/**
	 * Clicks to start a round at any given stage of the reaction test.
	 */
	public void clickToStart() {
		if (turn == 0) {
			wait.until(ExpectedConditions.visibilityOf(startShowing));
			playSpace.click();
		} else if (turn < MAX_TURNS) {
			wait.until(ExpectedConditions.visibilityOf(resultShowing));
			playSpace.click();
		} else {
			wait.until(ExpectedConditions.visibilityOf(scoreShowing));
			playSpace.click();
			turn = 0;
			scores = new int[5];
		}
	}

	/**
	 * Waits until the green click screen is showing and clicks.
	 * 
	 * @return	Integer value for the current round played.
	 */
	public Integer clickOnGreen() {
		action.moveToElement(playSpace).build().perform();
		wait.until(ExpectedConditions.visibilityOf(clickShowing));
		action.click().build().perform();
		turn++;
		int score = getScore();
		scores[turn - 1] = score;
		return score;
	}

	/**
	 * Gets score value for current round played. Calculates last round's individual
	 * value based on average given.
	 * 
	 * @return	Integer value for the current round played.
	 */
	private Integer getScore() {
		if (turn < MAX_TURNS) {
			// wait.until(ExpectedConditions.visibilityOf(resultShowing));
			return Integer.valueOf(individualResult.getText().split(" ")[0]);
		} else {
			int finalScore = Integer.valueOf(fullResult.getText().split("ms")[0]);
			int lastScore = finalScore * 5;
			for (int i = 0; i < MAX_TURNS; i++) {
				lastScore -= scores[i];
			}
			return lastScore;
		}
	}

	/**
	 * Gets average score for all rounds played.
	 * 
	 * @return	Integer value for average score.
	 */
	public Integer getEndResult() {
		if (turn >= MAX_TURNS) {
			// wait.until(ExpectedConditions.visibilityOf(scoreShowing));
			return Integer.valueOf(fullResult.getText().split("ms")[0]);
		} else {
			return -1;
		}
	}

}