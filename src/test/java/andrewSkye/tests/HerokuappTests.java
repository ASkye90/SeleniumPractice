package andrewSkye.tests;


import java.util.List;
import java.util.Random;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import andrewSkye.baseObjects.BaseTest;
import andrewSkye.herokuapp.pages.ABTestingPage;
import andrewSkye.herokuapp.pages.DynamicContentPage;
import andrewSkye.herokuapp.pages.HerokuappMainPage;
import andrewSkye.herokuapp.pages.HoversPage;
import andrewSkye.herokuapp.pages.HoversProfilePage;

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
	 * Checks if the page can dynamically load with duplicate elements.
	 */
	@Test
	public void testDynamicPage() {
		DynamicContentPage dynamicPage = mainPage.goToDynamicContentPage();

		boolean foundDuplicates = dynamicPage.refreshUntilDuplicateAvatars(3, 50);
		softAssert.assertTrue(foundDuplicates, "Unable to find page with 3 repeated Avatars within 50 refreshes. ");
		softAssert.assertAll();
	}
	
	/*
	 * Clicks into a random user profile.
	 * Checks if user profile matches with the user clicked.
	 */
	@Test
	public void testHoversPage() {
		HoversPage hoversPage = mainPage.goToHoversPage();
		List<String> users = hoversPage.getAllUserNames();
		
		Random random = new Random();
		int randIndex = random.nextInt(users.size());
		String user = users.get(randIndex);
		HoversProfilePage profilePage = hoversPage.goToProfile(user);
		String profileUrl = profilePage.getUrl();
		
		softAssert.assertEquals(user.charAt(user.length()-1),profileUrl.charAt(profileUrl.length()-1));
		softAssert.assertAll();
	}
}
