package andrewSkye.resources;

import java.time.LocalDate;
import java.time.LocalTime;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * Class to generate an Extent Report
 * 
 * @author Andrew Skye
 */
public class ExtentReporter {

	/**
	 * Get a newly generated Extent Report
	 * 
	 * @return	The Extent Report
	 */
	public static ExtentReports getReportObject() {
		LocalDate today = LocalDate.now();
		LocalTime time = LocalTime.now();
		String month = String.format("%02d", today.getMonth().getValue());
		String day = String.format("%02d", today.getDayOfMonth());
		String year = Integer.toString(today.getYear());
		String hour = String.format("%02d", time.getHour());
		String minute = String.format("%02d", time.getMinute());
		String pathDate = month + "." + day + "." + year + "." + hour + "." + minute;

		String path = System.getProperty("user.dir") + "//reports//" + pathDate + ".html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Automation Test Results");
		reporter.config().setDocumentTitle("Test Results");

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Andrew Skye");
		return extent;
	}
}
