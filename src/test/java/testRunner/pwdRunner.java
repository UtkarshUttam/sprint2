package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterClass;
import hooks.ReportHooks;

/**
 * pwdRunner - Main Test Suite Runner
 * 
 * Purpose:
 * - Runs all feature files in the test suite
 * - Generates comprehensive reports
 * - Used for full regression testing
 * 
 * Reports Generated:
 * - Extent Report: test-output/ExtentReport.html (with screenshots)
 * - Cucumber Report: target/cucumber-report.html
 * - TestNG Report: target/surefire-reports/index.html
 */
@CucumberOptions(
    features = "src/test/resources/features",   // 🗂 All feature files
    glue = {"stepDefinitions", "hooks"},        // 🧩 Step definitions and hooks
    plugin = {
        "pretty",                               // Console output
        "html:target/cucumber-report.html",     // Cucumber HTML report
        "json:target/cucumber-report.json"      // Cucumber JSON report
    },
    monochrome = true                           // Clean console output
)
public class pwdRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false) // Set to true for parallel execution (if needed)
    public Object[][] scenarios() {
        return super.scenarios();
    }
    
    @AfterClass
    public void cleanup() {
        // Ensure all reports are properly generated
        ReportHooks.ensureReportFlushed();
        System.out.println("=== Full Test Suite Execution Completed ===");
        System.out.println("📊 Reports Generated:");
        System.out.println("   • Extent Report: test-output/ExtentReport.html");
        System.out.println("   • Cucumber Report: target/cucumber-report.html");
        System.out.println("   • TestNG Report: target/surefire-reports/index.html");
        System.out.println("📸 Screenshots: screenshots/ folder");
    }
}
