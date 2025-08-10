package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;

public class ExtentManager {

    private static volatile ExtentReports extent;
    private static final String REPORT_PATH = "test-output/ExtentReport.html";
    private static boolean isInitialized = false;

    public static ExtentReports getInstance() {
        if (extent == null) {
            synchronized (ExtentManager.class) {
                if (extent == null) {
                    createInstance();
                }
            }
        }
        return extent;
    }

    private static void createInstance() {
        if (isInitialized) {
            return; // Prevent multiple initializations
        }
        
        // Clean up any existing report file
        File reportFile = new File(REPORT_PATH);
        if (reportFile.exists()) {
            reportFile.delete();
        }
        
        // Ensure the test-output directory exists
        File reportDir = new File("test-output");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        ExtentSparkReporter reporter = new ExtentSparkReporter(REPORT_PATH);
        reporter.config().setReportName("Sprint2 Test Report");
        reporter.config().setDocumentTitle("Automation Execution");
        reporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Project", "Sprint2");
        extent.setSystemInfo("Tester", "Utkarsh Uttam");
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        
        isInitialized = true;
        System.out.println("ExtentReports initialized successfully");
    }

    public static synchronized void flush() {
        if (extent != null && isInitialized) {
            extent.flush();
            System.out.println("ExtentReports flushed successfully");
        }
    }
    
    public static synchronized void reset() {
        extent = null;
        isInitialized = false;
    }
}
