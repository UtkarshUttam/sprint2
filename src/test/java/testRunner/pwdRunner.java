package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features",   // 🗂 Location of your feature file(s)
    glue = {"stepDefinitions", "hooks"},        // 🧩 Your step + hook packages
    plugin = {
        "pretty",
        "html:target/cucumber-report.html",
        "json:target/cucumber-report.json"
    },
    monochrome = true
)
public class pwdRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false) // Change to true for parallel scenario execution
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
