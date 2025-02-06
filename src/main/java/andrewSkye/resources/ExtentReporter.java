package andrewSkye.resources;

import java.time.LocalDate;
import java.time.LocalTime;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {

	public static ExtentReports getReportObject() {
		LocalDate today = LocalDate.now();
		LocalTime time = LocalTime.now();
		String pathDate = today.getMonth().getValue() + "." + today.getDayOfMonth() + "." + today.getYear() + "." + time.getHour() + "." + time.getMinute();
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
