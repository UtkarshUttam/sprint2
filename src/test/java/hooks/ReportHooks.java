package hooks;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import utils.ExtentManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.*;
import java.util.HashMap;
import java.util.Map;

public class ReportHooks {

    public static ExtentTest test;
    private static final Map<String, ExtentTest> scenarioMap = new HashMap<>();

    @Before
    public void startReport(Scenario scenario) {
        test = ExtentManager.getInstance().createTest(scenario.getName());
        scenarioMap.put(scenario.getName(), test);
    }

    @AfterStep
    public void logStep(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = Hooks.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            test.log(Status.FAIL, "Step failed").addScreenCaptureFromBase64String(
                new String(org.apache.commons.codec.binary.Base64.encodeBase64(screenshot)), "Failure Screenshot");
        } else {
            test.log(Status.PASS, "Step passed");
        }
    }

    @After
    public void flushReport() {
        ExtentManager.getInstance().flush();
    }
}
