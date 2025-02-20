package andrewSkye.tutorialsNinja;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The Product Page in Tutorials Ninja Demo website.
 * 
 * @author Andrew Skye
 */
public class ProductPage extends BaseTNPage {

	@FindBy(css = "#product>.form-group")
	private List<WebElement> availableOptions;

	@FindBy(id = "button-cart")
	private WebElement addToCart;

	@FindBy(className = "alert-success")
	private WebElement successMessage;

	@FindBy(className = "text-danger")
	private List<WebElement> errorMessages;

	private WebDriverWait wait;

	/**
	 * Creates a Product Page
	 * 
	 * @param driver WebDriver instance shared between pages within a test.
	 */
	public ProductPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	/**
	 * Enter all options into Available Options category on page. Uses
	 * TutorialsNinjaDefaultOption.properties if no option is supplied.
	 * 
	 * @param options Maps names of options to desired values for entry.
	 * @throws IOException Default properties file couldn't be retrieved.
	 */
	public void enterOptions(HashMap<String, String> options) {
		Properties properties = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(System.getProperty("user.dir")
					+ "//src//main//java//andrewSkye//tutorialsNinja//TutorialsNinjaDefaultOptions.properties");
			properties.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setting up separate date/time variables due to website holding various
		// date/time option names
		WebElement textField;
		String desiredDate = properties.getProperty("date");
		if (options.containsKey("date")) {
			desiredDate = options.get("date");
		}
		String desiredTime = properties.getProperty("time");
		if (options.containsKey("time")) {
			desiredTime = options.get("time");
		}

		// Iterate through all available options displayed on page
		for (WebElement option : availableOptions) {
			String optionName = option.findElement(By.tagName("label")).getText().toLowerCase();

			String desiredOption = properties.getProperty(optionName);
			if (options.containsKey(optionName)) {
				desiredOption = options.get(optionName);
			}

			// Handle each option type separately
			switch (optionName) {
				case "radio":
					List<WebElement> radioOptions = option.findElements(By.className("radio"));
					for (WebElement radioOption : radioOptions) {
						if (radioOption.getText().toLowerCase().contains(desiredOption)) {
							radioOption.findElement(By.tagName("input")).click();
							break;
						}
					}
					break;

				case "checkbox":
					List<String> desiredOptions = Arrays.asList(desiredOption.split(","));
					List<WebElement> checkboxOptions = option.findElements(By.className("checkbox"));
					for (WebElement checkboxOption : checkboxOptions) {
						String checkboxText = checkboxOption.getText().toLowerCase();
						for (String dO : desiredOptions) {
							if (checkboxText.contains(dO)) {
								checkboxOption.findElement(By.tagName("input")).click();
							}
						}
					}
					break;
				case "delivery date":
					desiredOption = desiredDate;
				case "date & time":
					desiredOption = desiredDate + " " + desiredTime;
				case "text":
				case "date":
				case "time":
				case "qty":
					textField = option.findElement(By.tagName("input"));
					textField.clear();
					textField.sendKeys(desiredOption);
					break;
				case "select":
					Select select = new Select(option.findElement(By.tagName("select")));
					for (WebElement selectOption : select.getOptions()) {
						if (selectOption.getText().toLowerCase().contains(desiredOption)) {
							select.selectByVisibleText(selectOption.getText());
						}
					}
					break;

				case "textarea":
					option.findElement(By.tagName("textarea")).sendKeys(desiredOption);
					break;

				case "file":
					String absolutePath = System.getProperty("user.dir") + desiredOption;

//					 Disable the click functions to stop non-interactable file explorer from
//					 popping up.
					((JavascriptExecutor) driver).executeScript("HTMLInputElement.prototype.click = function(){}");
					option.findElement(By.tagName("button")).click();

					// Make form with file input visible
					((JavascriptExecutor) driver)
							.executeScript("document.querySelector('form#form-upload').style.display='block'");
					wait.until(
							ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[type='file']"))));
					driver.findElement(By.cssSelector("input[type='file']")).sendKeys(absolutePath);

					wait.until(ExpectedConditions.alertIsPresent());
					Alert alert = driver.switchTo().alert();
					alert.accept();

//					 Code to re-hide html file input and restore click functionality in case we
//					 need this later.
//					((JavascriptExecutor) driver)
//							.executeScript("document.querySelector('form#form-upload').style.display='none'");
//					((JavascriptExecutor) driver).executeScript("delete HTMLInputElement.prototype.click");
					break;
			}
		}
		
		//Forcing page to update with all entered options by clicking into it.
		driver.findElement(By.cssSelector("#content h1")).click();
	}

	/**
	 * Clicks the Add to Cart button
	 * 
	 * @return	Any success, failure or warnings shown after clicking the button.
	 */
	public String clickAddToCart() {
		addToCart.click();
		String message = "Failed to click the add to cart button";
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(successMessage),
				ExpectedConditions.visibilityOfAllElements(errorMessages)));
		if (successMessage != null && successMessage.isDisplayed()) {
			message = successMessage.getText();
		} else if (errorMessages != null) {
			message = "";
			for (WebElement error : errorMessages) {
				message.concat(error.getText() + "\n");
			}
		}
		return message;
	}

}
