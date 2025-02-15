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

public class ProductPage extends BaseTNPage {

	@FindBy(css = "#product>.form-group")
	private List<WebElement> availableOptions;
	
	@FindBy(id="button-cart")
	private WebElement addToCart;
	
	@FindBy(className="alert-success")
	private WebElement successMessage;
	
	@FindBy(className="text-danger")
	private List<WebElement> errorMessages;	
	

	private WebDriverWait wait;

	public ProductPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	public void enterOptions(HashMap<String, String> options) throws IOException {
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "//src//main//java//andrewSkye//tutorialsNinja//TutorialsNinjaDefaultOptions.properties");
		properties.load(fis);

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

				case "text":
				case "date":
				case "time":
				case "qty":
					WebElement textBox = option.findElement(By.tagName("input"));
					textBox.clear();
					textBox.sendKeys(desiredOption);
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

					// Disable the click functions to stop non-interactable explorer from popping
					// up.
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

					// Code to re-hide file input and re-enable click functionality in case we need
					// it later.
					// ((JavascriptExecutor)
					// driver).executeScript("document.querySelector('form#form-upload').style.display='none'");
					// ((JavascriptExecutor) driver).executeScript("delete
					// HTMLInputElement.prototype.click");
					break;

				case "date & time":
					String desiredDate = properties.getProperty("date");
					if (options.containsKey("date")) {
						desiredDate = options.get("date");
					}
					String desiredTime = properties.getProperty("time");
					if (options.containsKey("time")) {
						desiredTime = options.get("time");
					}

					WebElement textField = option.findElement(By.tagName("input"));
					textField.clear();
					textField.sendKeys(desiredDate + " " + desiredTime);
					break;
			}
		}
	}

	public String clickAddToCart() {
		addToCart.click();
		String message = "Failed to clicked the cart button";
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(successMessage),ExpectedConditions.visibilityOfAllElements(errorMessages)));
		if (successMessage != null && successMessage.isDisplayed()) {
			message = successMessage.getText();
		} else if (errorMessages != null) {
			message = "";
			for (WebElement error: errorMessages) {
				message.concat(error.getText() + "\n");
			}
		}
		return message;
	}

}
