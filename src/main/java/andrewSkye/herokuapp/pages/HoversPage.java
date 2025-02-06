package andrewSkye.herokuapp.pages;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import andrewSkye.baseObjects.BasePage;

public class HoversPage extends BasePage {

	private Actions action;
	private WebDriverWait wait;
	private HashMap<String, WebElement> userToFigure;

	@FindBy(css = "div.figure")
	private List<WebElement> figures;

	public HoversPage(WebDriver driver) {
		super(driver);
		action = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		userToFigure = mapAllUsers();
	}

	/*
	 * Hovers over each figure on the page and then maps their corresponding user
	 * name to them.
	 * 
	 * @return HashMap mapping String user name to WebElement figure.
	 */
	private HashMap<String, WebElement> mapAllUsers() {
		HashMap<String, WebElement> newUserToImage = new HashMap<String, WebElement>();
		wait.until(ExpectedConditions.visibilityOfAllElements(figures));
		for (WebElement figure : figures) {
			action.moveToElement(figure).build().perform();
			String user = figure.findElement(By.tagName("h5")).getText().split(" ")[1];
			newUserToImage.put(user, figure);
		}
		return newUserToImage;
	}

	/*
	 * @return List of all users visible when hovering over images on the page.
	 */
	public List<String> getAllUserNames() {
		return userToFigure.keySet().stream().collect(Collectors.toList());
	}

	/*
	 * Switches to profile page of given user.
	 * 
	 * @param user Given user.
	 * 
	 * @return HoversProfilePage New profile page sharing same driver.
	 */
	public HoversProfilePage goToProfile(String user) {

		WebElement figure = userToFigure.get(user);
		action.moveToElement(figure).build().perform();
		figure.findElement(By.tagName("a")).click();
		return new HoversProfilePage(driver);
	}

}
