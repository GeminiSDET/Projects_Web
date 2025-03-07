package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extent == null) {
        	String path=System.getProperty("user.dir")+"//extent-reports//YahooFinanceTestReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            reporter.config().setReportName("Yahoo Finance Report");
            reporter.config().setDocumentTitle("Test Result");
            extent.setSystemInfo("Run By","Azar");
            extent.createTest(path);
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
    	ExtentTest extentTest=getInstance().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }
    public static ExtentTest getTest() {
    	return test.get();
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
