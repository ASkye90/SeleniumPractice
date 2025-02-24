package andrewSkye.resources;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

/**
 * Class for custom IRetryAnalyzer methods to run with tests.
 * 
 * @author Andrew Skye
 */
public class TestNGRetry implements IRetryAnalyzer {

	int count = 0;
	int maxTry = 2;

	/**
	 * Takes a screenshot of the current browser state
	 * 
	 * @param driver WebDriver instance running the current test.
	 * @return Base64 screenshot as a String
	 */
	private String getScreenshot(WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		return ts.getScreenshotAs(OutputType.BASE64);
	}

	/**
	 * Returns true if the test method has to be retried, false otherwise.
	 *
	 * @param result The result of the test method that just ran.
	 * @return true if the test method has to be retried, false otherwise.
	 */
	@Override
	public boolean retry(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("extentTest");
		if (count < maxTry) {
			count++;
			test.log(Status.WARNING, result.getThrowable());
			try {
				WebDriver driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
						.get(result.getInstance());
				test.addScreenCaptureFromBase64String(getScreenshot(driver));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			test.log(Status.INFO, "Retrying test due to flaky test failure.");
			return true;
		}
		return false;
	}
}
