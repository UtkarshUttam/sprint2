package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
// import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import hooks.Hooks;
import base.BaseClass;
import pageObjects.loginPage;
import utils.ExtentManager;
import utils.ScreenshotUtil;
import utils.WaitUtility;
import utils.DataReader;

import java.util.List;
import java.util.Map;

public class PasswordFieldSteps {

    private WebDriver driver;
    private loginPage login;
    private ExtentReports extent = ExtentManager.getInstance();
    private ExtentTest test;

    @Before
    public void beforeScenario(Scenario scenario) {
        // Start a new test in the report matching the Cucumber scenario name
        test = extent.createTest(scenario.getName());
    }

    @Given("I open the login page")
    public void i_open_the_login_page() {
        driver = Hooks.getDriver();
        login = new loginPage(driver);
        driver.get(BaseClass.getBaseUrl());
        test.log(Status.INFO, "Navigated to login page: " + BaseClass.getBaseUrl());
    }

    @When("I validate password of type {string} using data from {string}")
    public void validate_password_of_type_using_data_from(String type, String csvPath) {
        test.log(Status.INFO, "Reading test data for password type: " + type);
        List<Map<String, String>> testCases = DataReader.readCsv(csvPath);

        Map<String, String> tc = testCases.stream()
            .filter(row -> row.get("Type").equalsIgnoreCase(type))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No test case for type: " + type));

        String input    = tc.get("Input");
        String expected = tc.get("ExpectedOutcome").replace("Error: ", "").trim();

        // Prepare the form
        login.getPasswordField().clear();
        if (!input.isEmpty()) {
            login.setPassword(input);
        }
        login.setUsername(BaseClass.getUsername());
        login.clickSignIn();
        test.log(Status.INFO, "Submitted form with password: " + (input.isEmpty() ? "<blank>" : "<provided>"));

        try {
            if ("Success".equalsIgnoreCase(expected)) {
                boolean errorDisplayed = driver.findElements(By.id("passwordError")).size() > 0;
                ScreenshotUtil.takeScreenshot(driver, "Pass_" + type, false);

                if (!errorDisplayed) {
                    test.pass("[" + type + "] Passed: no error displayed");
                } else {
                    test.fail("[" + type + "] FAILED: unexpected error shown");
                    throw new AssertionError("Expected success for " + type + " password");
                }
            } else {
                String actual = WaitUtility
                    .waitForVisible(driver, login.getPasswordError())
                    .getText().trim();

                validateError(type, expected, actual);
            }
        } catch (AssertionError ae) {
            test.log(Status.FAIL, "Assertion failed: " + ae.getMessage());
            throw ae;
        } catch (Exception ex) {
            test.log(Status.FAIL, "Exception during validation: " + ex.getMessage());
            throw ex;
        }
    }

    @Then("validation should be asserted")
    public void final_validation() {
        test.log(Status.INFO, "Final validation step (all assertions handled in @When)");
    }

    @After
    public void afterScenario() {
        extent.flush();
    }

    // Helper for logging errors and attaching screenshots
    private void validateError(String type, String expected, String actual) {
        String label = "[" + type + "]";
        ScreenshotUtil.takeScreenshot(driver, "Error_" + type, false);

        if (actual.equals(expected)) {
            test.pass(label + " Passed: expected error '" + expected + "'");
        } else {
            test.fail(label + " FAILED: expected '" + expected + "' but got '" + actual + "'");
            throw new AssertionError("Validation failed for " + type + " password");
        }
    }
}
