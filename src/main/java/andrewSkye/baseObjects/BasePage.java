package andrewSkye.baseObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * The Base Page for all Pages to inherit.
 * 
 * @author Andrew Skye
 */
public class BasePage {

	protected WebDriver driver;

	/**
	 * Creates a Base Page with a 1 second implicit wait and initializes Page Factory objects.
	 * 
	 * @param	driver	WebDriver instance shared between pages	within a test.
	 */
	public BasePage(WebDriver driver) {
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		PageFactory.initElements(driver, this);
	}

	/**
	 * Get the title of the page
	 * 
	 * @return The title of the page.
	 */
	public String getTitle() {
		return driver.getTitle();
	}
	
	/**
	 * Get the url of the page
	 * 
	 * @return The url of the page.
	 */
	public String getUrl() {
		return driver.getCurrentUrl();
	}
}
