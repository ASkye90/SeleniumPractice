package andrewSkye.herokuapp.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import andrewSkye.baseObjects.BasePage;

public class DynamicContentPage extends BasePage {
	
	@FindBy(css="div#content img")
	List<WebElement> avatarImages;
	
	public DynamicContentPage(WebDriver driver) {
		super(driver);
	}
	
	/*
	 * Tries to find a specific Avatar picture on the page in a given number of page refreshes.
	 * 
	 * @param	Avatar		Name of Avatar to find
	 * @param	maxTries	Maximum number of times to try refreshing.
	 * 
	 * @return	First WebElement found containing Mario profile picture or null if none found. 
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
	 * @param	numAvatars	Number of duplicate Avatars to find			
	 * @param	maxTries	Maximum number of times to try refreshing.
	 * 
	 * @return True if page found with numAvatars Avatars within the given maxTries.
	 */
	public boolean refreshUntilDuplicateAvatars(int numAvatars, int maxTries) {
		int refreshes = 0;
		do {
			int totalAvatars = avatarImages.size();
			long uniqueAvatars = avatarImages.stream().map(e->e.getDomAttribute("src")).distinct().count();
			
			
			if (uniqueAvatars <= totalAvatars - numAvatars + 1) {
				return true;
			}
			refreshes++;
			driver.navigate().refresh();
		} while (refreshes < maxTries);		
		return false;
	}
}
