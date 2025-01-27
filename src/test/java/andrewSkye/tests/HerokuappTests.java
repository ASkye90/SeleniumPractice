package andrewSkye.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import andrewSkye.baseObjects.BaseTest;
import andrewSkye.herokuapp.pages.ABTestingPage;
import andrewSkye.herokuapp.pages.DynamicContentPage;
import andrewSkye.herokuapp.pages.HerokuappMainPage;

public class HerokuappTests extends BaseTest {

	
	private HerokuappMainPage mainPage;
	
	
	@BeforeMethod
	public void goToMain() {
		mainPage = new HerokuappMainPage(driver);
		mainPage.goToMainPage();
	}
	
	/*
	 * Checking if the title and header are correct in AB Testing page
	 */
	@Test
	public void testABPage() {
		String expectedHeader = "A/B Test";
		String expectedTitle = "The Internet";

		ABTestingPage abPage = mainPage.goToABTestingPage();
		String pageTitle = abPage.getTitle();
		softAssert.assertEquals(expectedTitle, pageTitle);

		String pageHeader = abPage.getHeader();
		softAssert.assertTrue(pageHeader.contains(expectedHeader));
		softAssert.assertAll();
	}
	
	/*
	 * Flaky test
	 * Checking if the page can dynamically load with duplicate elements.
	 */
	@Test
	public void testDynamicPage() {
		DynamicContentPage dynamicPage = mainPage.goToDynamicContentPage();

		boolean foundDuplicates = dynamicPage.refreshUntilDuplicateAvatars(3, 50);
		softAssert.assertTrue(foundDuplicates, "Unable to find page with 3 repeated Avatars within 50 refreshes. ");
		softAssert.assertAll();
	}
	
}
