package andrewSkye.resources;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestNGRetry implements IRetryAnalyzer {

	int count = 0;
	int maxTry = 2;

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

	private String getScreenshot(WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		return ts.getScreenshotAs(OutputType.BASE64);
	}
}
