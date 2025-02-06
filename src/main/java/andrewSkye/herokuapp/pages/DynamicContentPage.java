package andrewSkye.herokuapp.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BasePage;

public class DynamicContentPage extends BasePage {

	@FindBy(css = "div#content img")
	private List<WebElement> avatarImages;

	public DynamicContentPage(WebDriver driver) {
		super(driver);
	}

	/*
	 * Tries to find a specific Avatar picture on the page in a given number of page
	 * refreshes.
	 * 
	 * @param Avatar Name of Avatar to find
	 * 
	 * @param maxTries Maximum number of times to try refreshing.
	 * 
	 * @return First WebElement found containing Mario profile picture or null if
	 * none found.
	 */
	public WebElement findAvatar(String avatar, int maxTries) {

		WebElement profilePic = null;
		int refreshes = 0;

		do {
			profilePic = avatarImages.stream().filter(webEle -> webEle.getDomAttribute("src").contains(avatar))
					.findFirst().orElse(null);
			if (profilePic == null) {
				refreshes++;
				driver.navigate().refresh();
			}
		} while (profilePic == null && refreshes < maxTries);
		return profilePic;
	}

	/*
	 * Refresh page until landing on one with repeated Avatar pictures.
	 * 
	 * @param numAvatars Number of duplicate Avatars to find
	 * 
	 * @param maxTries Maximum number of times to try refreshing.
	 * 
	 * @return Number of refreshes executed.
	 */
	public Integer refreshUntilDuplicateAvatars(int numAvatars, int maxTries) {
		int refreshes = 0;
		do {
			refreshes++;
			driver.navigate().refresh();
		} while (refreshes < maxTries && !hasNumDuplicateAvatars(numAvatars));
		return refreshes;
	}

	/*
	 * Check if the current page has repeated Avatar pictures.
	 * 
	 * @param numAvatars Number of duplicate Avatars to check for
	 * 
	 * @return True if page contains numAvatars duplicate Avatars.
	 */
	public Boolean hasNumDuplicateAvatars(int numAvatars) {
		int totalAvatars = avatarImages.size();
		long uniqueAvatars = avatarImages.stream().map(e -> e.getDomAttribute("src")).distinct().count();
		return uniqueAvatars <= totalAvatars - numAvatars + 1;
	}
}
