package andrewSkye.tests;

import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import andrewSkye.baseObjects.BaseTest;
import andrewSkye.herokuapp.ABTestingPage;
import andrewSkye.herokuapp.DynamicContentPage;
import andrewSkye.herokuapp.HerokuappMainPage;
import andrewSkye.herokuapp.HoversPage;
import andrewSkye.herokuapp.HoversProfilePage;
import andrewSkye.resources.TestNGRetry;

/**
 * Tests for The-Internet Herokuapp website.
 * 
 * @author Andrew Skye
 */
public class HerokuappTests extends BaseTest {

	private String url = "https://the-internet.herokuapp.com/";

	/**
	 * Load into the Main Page.
	 * 
	 * @return	The Main Page
	 */
	private HerokuappMainPage goToMainPage() {
		if (driver.getCurrentUrl() != url) {
			driver.get(url);
		}
		return new HerokuappMainPage(driver);
	}

	/**
	 * Checks if the title and header are correct in AB Testing page
	 * 
	 * @param context 	Test context that is currently running
	 */
	@Test
	public void testABPage(ITestContext context) {
		HerokuappMainPage mainPage = goToMainPage();
		String expectedHeader = "A/B Test";
		String expectedTitle = "The Internet";
		ExtentTest extentTest = createExtentTest("Hrku - AB Page",
				"Checks the page title is " + expectedTitle + " and page header contains " + expectedHeader + ".",
				context);

		extentTest.log(Status.INFO, "Clicking into AB Page");
		ABTestingPage abPage = mainPage.goToABTestingPage();

		extentTest.log(Status.INFO, "Collecting page title");
		String pageTitle = abPage.getTitle();
		softAssert.assertEquals(expectedTitle, pageTitle);

		extentTest.log(Status.INFO, "Collecting page header");
		String pageHeader = abPage.getHeader();
		softAssert.assertTrue(pageHeader.contains(expectedHeader));
		softAssert.assertAll();
	}

	/**
	 * Flaky test 
	 * Checks if the page can dynamically load with duplicate elements.
	 * 
	 * @param context 	Test context that is currently running
	 */
	@Test(retryAnalyzer = TestNGRetry.class)
	public void testDynamicPage(ITestContext context) {
		HerokuappMainPage mainPage = goToMainPage();
		int numDuplicates = 3;
		int maxRefreshes = 50;
		ExtentTest extentTest = createExtentTest("Hrku - Dynamic Content Page", "Checks if user can find a page with "
				+ numDuplicates + " duplicated avatars within " + maxRefreshes + " refreshes.", context);

		extentTest.log(Status.INFO, "Clicking into Dynamic Content Page");
		DynamicContentPage dynamicPage = mainPage.goToDynamicContentPage();

		extentTest.log(Status.INFO, "Refreshing page up to " + maxRefreshes + " times");
		int numRefreshes = dynamicPage.refreshUntilDuplicateAvatars(numDuplicates, maxRefreshes);
		extentTest.log(Status.INFO, "Refreshed page " + numRefreshes + " times");

		extentTest.log(Status.INFO, "Checking if duplicates exist");
		Assert.assertTrue(dynamicPage.hasNumDuplicateAvatars(numDuplicates), "Unable to find page with " + numDuplicates
				+ " repeated Avatars within " + maxRefreshes + " refreshes. ");
	}

	/**
	 * Clicks into a random user profile. 
	 * Checks if user profile matches with the user clicked.
	 * 
	 * @param context 	Test context that is currently running
	 */
	@Test
	public void testHoversPage(ITestContext context) {
		HerokuappMainPage mainPage = goToMainPage();
		ExtentTest extentTest = createExtentTest("Hrku - Hovers Page",
				"Clicks into a random user profile and checks if it loads the correct profile.", context);

		extentTest.log(Status.INFO, "Clicking into Hovers Page");
		HoversPage hoversPage = mainPage.goToHoversPage();

		extentTest.log(Status.INFO, "Collecting user information");
		List<String> users = hoversPage.getAllUserNames();

		extentTest.log(Status.INFO, "Clicking into a random user profile page");
		Random random = new Random();
		int randIndex = random.nextInt(users.size());
		String user = users.get(randIndex);
		HoversProfilePage profilePage = hoversPage.goToProfile(user);

		String profileUrl = profilePage.getUrl();
		Assert.assertEquals(user.charAt(user.length() - 1), profileUrl.charAt(profileUrl.length() - 1));
	}
}
