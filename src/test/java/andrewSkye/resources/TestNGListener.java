package andrewSkye.resources;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

/**
 * Class for custom ITestListener methods to run with tests.
 * 
 * @author Andrew Skye
 */
public class TestNGListener implements ITestListener {

	/**
	 * Takes a screenshot of the current browser state
	 * 
	 * @param driver	WebDriver instance running the current test.
	 * @return		Base64 screenshot as a String
	 */
	private String getScreenshot(WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		return ts.getScreenshotAs(OutputType.BASE64);
	}

	  /**
	   * Invoked each time a test fails.
	   * Takes a screenshot and attaches it to the test report.
	   *
	   * @param result <code>ITestResult</code> containing information about the run test
	   * @see ITestResult#FAILURE
	   */
	public void onTestFailure(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("extentTest");
		test.log(Status.FAIL, result.getThrowable());
		try {
			WebDriver driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
			test.addScreenCaptureFromBase64String(getScreenshot(driver));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	  /**
	   * Invoked each time a test succeeds.
	   *
	   * @param result <code>ITestResult</code> containing information about the run test
	   * @see ITestResult#SUCCESS
	   */
	public void onTestSuccess(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("extentTest");
		test.log(Status.PASS, "Test Passed");
	}
}
