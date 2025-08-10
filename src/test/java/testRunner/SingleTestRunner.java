package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterClass;
import hooks.ReportHooks;

/**
 * SingleTestRunner - For Development and Debugging
 * 
 * Purpose:
 * - Run individual scenarios for testing
 * - Debug specific features without running the full suite
 * - Quick validation during development
 * 
 * Usage:
 * - Modify the 'features' path to target specific feature files
 * - Use 'tags' to run specific scenarios (e.g., "@smoke", "@regression")
 * - Change features path to run different feature files
 */
@CucumberOptions(
<<<<<<< Updated upstream
    features = "src/test/resources/features/St_NameReg.feature", // Target specific feature - change as needed
=======
    features = "src/test/resources/features/St_CGPAReg.feature", // Target specific feature - change as needed
>>>>>>> Stashed changes
    glue = {"stepDefinitions", "hooks"},
    plugin = {
        "pretty",
        "html:target/single-test-report.html", // Separate report for single tests
        "json:target/single-test-report.json"
    },
    monochrome = true
    // tags = "@smoke" // Uncomment and modify to run specific tagged scenarios
)
public class SingleTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
    
    @AfterClass
    public void cleanup() {
        // Ensure report gets flushed for single test runs
        ReportHooks.ensureReportFlushed();
        System.out.println("=== Single Test Execution Completed ===");
        System.out.println("Extent Report: test-output/ExtentReport.html");
        System.out.println("Cucumber Report: target/single-test-report.html");
    }
}