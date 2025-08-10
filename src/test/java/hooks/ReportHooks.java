package hooks;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;
import utils.ExtentManager;
import utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.*;
import java.util.Base64;

public class ReportHooks {

    public static ExtentTest test;
    private static boolean isReportInitialized = false;

    @Before(order = 0)
    public void startReport(Scenario scenario) {
        // Initialize report only once
        if (!isReportInitialized) {
            ExtentManager.reset();
            isReportInitialized = true;
            System.out.println("Extent Report initialized");
            
            // Add shutdown hook to ensure report gets flushed
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                ensureReportFlushed();
            }));
        }
        
        // Create test for each scenario
        test = ExtentManager.getInstance().createTest(scenario.getName());
        test.log(Status.INFO, "Starting scenario: " + scenario.getName());
    }

    @After(order = 1000)
    public void afterScenario(Scenario scenario) {
        if (test != null) {
            WebDriver driver = Hooks.getDriver();
            
            if (scenario.isFailed()) {
                if (driver != null) {
                    try {
                        // Take screenshot using our utility
                        String base64Screenshot = ScreenshotUtil.takeScreenshotAsBase64(driver, "failed_" + scenario.getName().replaceAll("[^a-zA-Z0-9]", "_"), false);
                        
                        if (base64Screenshot != null) {
                            test.fail("Scenario failed", 
                                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                        } else {
                            test.log(Status.FAIL, "Scenario failed (screenshot capture failed)");
                        }
                    } catch (Exception e) {
                        test.log(Status.FAIL, "Scenario failed: " + e.getMessage());
                    }
                } else {
                    test.log(Status.FAIL, "Scenario failed (no driver available)");
                }
            } else {
                // Take screenshot for passed scenarios too
                if (driver != null) {
                    try {
                        String base64Screenshot = ScreenshotUtil.takeScreenshotAsBase64(driver, "passed_" + scenario.getName().replaceAll("[^a-zA-Z0-9]", "_"), true);
                        
                        if (base64Screenshot != null) {
                            test.pass("Scenario completed successfully", 
                                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                        } else {
                            test.log(Status.PASS, "Scenario completed successfully");
                        }
                    } catch (Exception e) {
                        test.log(Status.PASS, "Scenario completed successfully (screenshot capture failed): " + e.getMessage());
                    }
                } else {
                    test.log(Status.PASS, "Scenario completed successfully");
                }
            }
        }
    }

    // Backup flush mechanism in case TestNG listener doesn't work
    private static boolean reportFlushed = false;
    
    public static void ensureReportFlushed() {
        if (!reportFlushed && isReportInitialized) {
            ExtentManager.flush();
            reportFlushed = true;
            System.out.println("=== Backup flush: Extent Report generated at: test-output/ExtentReport.html ===");
        }
    }
}