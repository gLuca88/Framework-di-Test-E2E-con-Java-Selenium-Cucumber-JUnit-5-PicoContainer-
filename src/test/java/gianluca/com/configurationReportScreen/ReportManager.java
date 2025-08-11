package gianluca.com.configurationReportScreen;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {
	private static ExtentReports extent;
	private static final ThreadLocal<ExtentTest> current = new ThreadLocal<>();

	public static void init() {
		if (extent != null)
			return;
		ExtentSparkReporter reporter = new ExtentSparkReporter(PathManager.reportHtml().toString());
		reporter.config().setDocumentTitle("Ecommerce Test Report");
		reporter.config().setReportName("Esecuzione Tests");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

	public static void flush() {
		if (extent != null)
			extent.flush();
	}

	public static void startTest(String name) {
		current.set(extent.createTest(name));
	}

	public static ExtentTest test() {
		return current.get();
	}

	public static void endTest() {
		current.remove();
	}
}
