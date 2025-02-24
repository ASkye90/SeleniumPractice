package andrewSkye.baseObjects;

import java.io.FileInputStream;
import java.io.IOException;
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

/**
 * The Base Test for all Tests to inherit.
 * 
 * @author Andrew Skye
 */
public class BaseTest {

	protected WebDriver driver;
	protected SoftAssert softAssert;
	private static ExtentReports reporter = ExtentReporter.getReportObject();
	private String browser;

	/**
	 * Run before any test begins, starting a new WebDriver instance and attaching
	 * the ExtentReporter to the TestContext.
	 * 
	 * @param context 	Test context that is currently running
	 * @throws IOException		Global properties file couldn't be retrieved.
	 */
	@BeforeTest
	public void beforeTest(ITestContext context) throws IOException {

		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//andrewSkye//resources//Global.properties");
		properties.load(fis);
		browser = properties.getProperty("browser");

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

	/**
	 * Run after any test concludes.
	 * Close the WebDriver instance and generate a report.
	 */
	@AfterTest
	public void afterTest() {
		// Sleep for viewing test in progress during development.
		// Remove or replace with logic to see if test has been manually triggered?
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.close();
		reporter.flush();
	}

	/**
	 * Create a Test with a given name and description, then attach it to the
	 * TestContext.
	 * 
	 * @testName Name of the test
	 * 
	 * @testDescription Description of the test
	 */
	protected ExtentTest createExtentTest(String testName, String testDescription, String testCategory, ITestContext context) {
		ExtentTest extentTest = reporter.createTest(testName, testDescription);
		context.setAttribute("extentTest", extentTest);
		extentTest.assignAuthor("Andrew Skye");
		extentTest.assignDevice(browser);
		extentTest.assignCategory(testCategory);
		return extentTest;
	}
}
