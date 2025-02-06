package andrewSkye.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import andrewSkye.baseObjects.BaseTest;
import andrewSkye.humanBenchmark.HBMainPage;
import andrewSkye.humanBenchmark.ReactionTimePage;
import andrewSkye.humanBenchmark.TypingPage;

public class HumanBenchmarkTests extends BaseTest {

	private HBMainPage mainPage;

	@BeforeMethod
	public void goToMain() {
		mainPage = new HBMainPage(driver);
		mainPage.goToMainPage();
	}

	/*
	 * Runs a full set of turns. Checks if the individual and end results displayed
	 * are valid (above 0).
	 */
	@Test
	public void testSingleReactionGame() {
		ReactionTimePage reactionTime = mainPage.goToReactionTime();
		SoftAssert softAssert = new SoftAssert();
		int currentTurn = 0;
		int result;
		do {
			reactionTime.clickToStart();
			result = reactionTime.clickOnGreen();
			softAssert.assertTrue(result > 0, "Reaction time displayed as " + result);
			currentTurn++;
		} while (currentTurn < reactionTime.MAX_TURNS);
		int endResult = reactionTime.getEndResult();
		softAssert.assertTrue(endResult > 0, "Reaction time displayed as " + endResult);
		softAssert.assertAll();
	}

	/*
	 * Types the full typing test. Checks if the WPM result displayed is valid
	 * (above 0).
	 */
	@Test
	public void testTypingGame() {
		TypingPage typing = mainPage.goToTyping();
		int result = typing.typeTest();
		Assert.assertTrue(result > 0, "Reaction time displayed as " + result);
	}

}
