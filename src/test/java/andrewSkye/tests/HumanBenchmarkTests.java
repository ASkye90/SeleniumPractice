package andrewSkye.tests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import andrewSkye.baseObjects.BaseTest;
import andrewSkye.humanBenchmark.HBMainPage;
import andrewSkye.humanBenchmark.ReactionTimePage;
import andrewSkye.humanBenchmark.TypingPage;

/**
 * Tests for Human Benchmark website.
 * 
 * @author Andrew Skye
 */
public class HumanBenchmarkTests extends BaseTest {

	private String url = "https://humanbenchmark.com/";

	/**
	 * Load into the Main Page.
	 * 
	 * @return The Main Page
	 */
	private HBMainPage goToMainPage() {
		if (driver.getCurrentUrl() != url) {
			driver.get(url);
		}
		return new HBMainPage(driver);
	}

	/**
	 * Runs a full set of turns in reaction game. Checks if the individual and end
	 * results displayed are valid (above 0).
	 * 
	 * @param context Test context that is currently running
	 */
	@Test
	public void testSingleReactionGame(ITestContext context) {
		HBMainPage mainPage = goToMainPage();
		ExtentTest extentTest = createExtentTest("Reaction Game",
				"Runs a full 5 round reaction game and checks all reported times are above 0.", "Human Benchmark",
				context);

		extentTest.log(Status.INFO, "Clicking into Reaction Time");
		ReactionTimePage reactionTime = mainPage.goToReactionTime();

		int currentTurn = 0;
		int result;
		do {
			extentTest.log(Status.INFO, "Starting round " + (currentTurn + 1));
			reactionTime.clickToStart();

			result = reactionTime.clickOnGreen();
			extentTest.log(Status.INFO, "Completed round " + (currentTurn + 1) + " in " + result + "ms");

			softAssert.assertTrue(result > 0,
					"Round " + (currentTurn + 1) + " reaction time displayed as below 0, " + result + "ms");
			currentTurn++;
		} while (currentTurn < ReactionTimePage.MAX_TURNS);

		int endResult = reactionTime.getEndResult();
		extentTest.log(Status.INFO, "Completed game with " + endResult + "ms average");
		softAssert.assertTrue(endResult > 0, "Average reaction time displayed as below 0, " + endResult + "ms");
		softAssert.assertAll();
	}

	/**
	 * Types the full typing test. Checks if the WPM result displayed is valid
	 * (above 0).
	 * 
	 * @param context Test context that is currently running
	 */
	@Test
	public void testTypingGame(ITestContext context) {
		HBMainPage mainPage = goToMainPage();
		ExtentTest extentTest = createExtentTest("Typing Game",
				"Runs one typing round and checks the reported wpm is above 0.", "Human Benchmark", context);

		extentTest.log(Status.INFO, "Clicking into Typing Game");
		TypingPage typingPage = mainPage.goToTypingPage();

		extentTest.log(Status.INFO, "Collecting paragraph into String");
		String paragraph = typingPage.getFullParagraph();

		extentTest.log(Status.INFO, "Typing full paragraph");
		int result = typingPage.typeTest(paragraph);
		extentTest.log(Status.INFO, "Finished typing paragraph at " + result + "wpm");

		Assert.assertTrue(result > 0, "Typing time displayed as below 0, " + result + "wpm");
	}

}
