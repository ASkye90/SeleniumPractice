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
	
	private HashMap<String,WebElement> mapAllUsers() {
		HashMap<String,WebElement> newUserToImage = new HashMap<String,WebElement>();
		for (WebElement figure:figures) {
			action.moveToElement(figure).build().perform();
			String user = figure.findElement(By.tagName("h5")).getText().split(" ")[1];
			newUserToImage.put(user, figure);
		}
		return newUserToImage;
	}
	
	public List<String> getAllUserNames() {
		return userToFigure.keySet().stream().collect(Collectors.toList());
	}

	public HoversProfilePage goToProfile(String user) {
		
		WebElement figure = userToFigure.get(user);
		action.moveToElement(figure).build().perform();
		figure.findElement(By.tagName("a")).click();
		return new HoversProfilePage(driver);
	}
	
	
	
}
