package andrewSkye.herokuapp.pages;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import andrewSkye.baseObjects.BasePage;

public class HoversPage extends BasePage {

	Actions action;

	@FindBy(css="div.figure")
	List<WebElement> figures;
	
	HashMap<String,WebElement> userToFigure;
	
	public HoversPage(WebDriver driver) {
		super(driver);
		action = new Actions(driver);
		userToFigure = mapAllUsers();
	}
	
	/*
	 * Hovers over each figure on the page and then maps their corresponding user name to them.
	 * 
	 * @return HashMap mapping String user name to WebElement figure.
	 */
	private HashMap<String,WebElement> mapAllUsers() {
		HashMap<String,WebElement> newUserToImage = new HashMap<String,WebElement>();
		for (WebElement figure:figures) {
			action.moveToElement(figure).build().perform();
			String user = figure.findElement(By.tagName("h5")).getText().split(" ")[1];
			newUserToImage.put(user, figure);
		}
		return newUserToImage;
	}
	
	/*
	 * @return	List of all users visible when hovering over images on the page.
	 */
	public List<String> getAllUserNames() {
		return userToFigure.keySet().stream().collect(Collectors.toList());
	}

	/*
	 * Switches to profile page of given user.
	 * 
	 * @param	user	Given user.
	 * @return	HoversProfilePage	New profile page sharing same driver.
	 */
	public HoversProfilePage goToProfile(String user) {
		
		WebElement figure = userToFigure.get(user);
		action.moveToElement(figure).build().perform();
		figure.findElement(By.tagName("a")).click();
		return new HoversProfilePage(driver);
	}
	
	
	
}
