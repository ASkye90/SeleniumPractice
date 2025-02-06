package andrewSkye.baseObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import andrewSkye.resources.ExtentReporter;

public class BaseTest {

	public WebDriver driver;
	protected SoftAssert softAssert;
	protected static ExtentReports reporter = ExtentReporter.getReportObject();

	@BeforeTest
	public void beforeTest(ITestContext context) {
		driver = new ChromeDriver();

//		WebDriver driver = new FirefoxDriver();
//		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		softAssert = new SoftAssert();
		context.setAttribute("reporter", reporter);
	}

	@AfterTest
	public void afterTest() {
		driver.close();
		reporter.flush();
	}

	protected ExtentTest createExtentTest(String testName, String testDescription, ITestContext context) {
		ExtentTest extentTest = reporter.createTest(testName,testDescription);
		context.setAttribute("extentTest", extentTest);
		return extentTest;
	}
}
