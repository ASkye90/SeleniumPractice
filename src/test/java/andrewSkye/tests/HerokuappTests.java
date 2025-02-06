package andrewSkye.tests;

import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import andrewSkye.baseObjects.BaseTest;
import andrewSkye.herokuapp.pages.ABTestingPage;
import andrewSkye.herokuapp.pages.DynamicContentPage;
import andrewSkye.herokuapp.pages.HerokuappMainPage;
import andrewSkye.herokuapp.pages.HoversPage;
import andrewSkye.herokuapp.pages.HoversProfilePage;
import andrewSkye.resources.TestNGRetry;

public class HerokuappTests extends BaseTest {

	private HerokuappMainPage mainPage;

	@BeforeMethod
	public void goToMain() {
		mainPage = new HerokuappMainPage(driver);
		mainPage.goToMainPage();
	}

	/*
	 * Checks if the title and header are correct in AB Testing page
	 */
	@Test
	public void testABPage(ITestContext context) {
		String expectedHeader = "A/B Test";
		String expectedTitle = "The Internet";
		ExtentTest extentTest = createExtentTest("AB Page",
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

	/*
	 * Flaky test Checks if the page can dynamically load with duplicate elements.
	 */
	@Test(retryAnalyzer = TestNGRetry.class)
	public void testDynamicPage(ITestContext context) {
		int numDuplicates = 3;
		int maxRefreshes = 50;
		ExtentTest extentTest = createExtentTest("Dynamic Content Page", "Checks if user can find a page with "
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

	/*
	 * Failure test Checks an impossible scenario that is expected to report a test
	 * failure every time.
	 */
	@Test
	public void testDynamicPageFailure(ITestContext context) {
		ExtentTest extentTest = createExtentTest("Dynamic Content Page Failure",
				"Checks if user can find a page with 4 duplicated avatars within 5 refreshes (impossible condition).",
				context);

		extentTest.log(Status.INFO, "Clicking into Dynamic Content Page");
		DynamicContentPage dynamicPage = mainPage.goToDynamicContentPage();

		extentTest.log(Status.INFO, "Refreshing page up to 5 times");
		int numRefreshes = dynamicPage.refreshUntilDuplicateAvatars(4, 5);
		extentTest.log(Status.INFO, "Refreshed page " + numRefreshes + " times");

		extentTest.log(Status.INFO, "Checking if impossible condition somehow occurred");
		Assert.assertTrue(dynamicPage.hasNumDuplicateAvatars(4),
				"Unable to find page with 4 repeated Avatars within 5 refreshes.");
	}

	/*
	 * Clicks into a random user profile. Checks if user profile matches with the
	 * user clicked.
	 */
	@Test
	public void testHoversPage(ITestContext context) {
		ExtentTest extentTest = createExtentTest("Hovers Page",
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
