package andrewSkye.baseObjects;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

	/*
	 * Start a new WebDriver in given browser and attach the ExtentReporter to the
	 * TestContext.
	 */
	@BeforeTest
	public void beforeTest(ITestContext context) throws IOException {

		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//andrewSkye//resources//Global.properties");
		properties.load(fis);
		String browser = properties.getProperty("browser");

		switch (browser) {
		case "chromeheadless":
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless=new");
			driver = new ChromeDriver(options);
			break;
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			driver = new ChromeDriver();
		}

		driver.manage().window().maximize();
		softAssert = new SoftAssert();
		context.setAttribute("reporter", reporter);
	}

	/*
	 * Close the WebDriver and generate the report.
	 */
	@AfterTest
	public void afterTest() throws InterruptedException {
		Thread.sleep(5000);
		driver.close();
		reporter.flush();
	}

	/*
	 * Create a Test with a given name and description, then attach it to the
	 * TestContext.
	 * 
	 * @testName Name of the test
	 * 
	 * @testDescription Description of the test
	 */
	protected ExtentTest createExtentTest(String testName, String testDescription, ITestContext context) {
		ExtentTest extentTest = reporter.createTest(testName, testDescription);
		context.setAttribute("extentTest", extentTest);
		return extentTest;
	}
}
