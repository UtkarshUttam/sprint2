package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.BaseClass;
import hooks.Hooks;
import pageObjects.RegistrationPage;
import pageObjects.loginPage;
import utils.DataReader;
import utils.ExtentManager;
import utils.ScreenshotUtil;
import utils.WaitUtility;

import java.util.List;
import java.util.Map;

public class RegistrationSteps {

    private WebDriver driver;
    private loginPage login;
    private RegistrationPage regPage;
    private ExtentReports extent = ExtentManager.getInstance();
    private ExtentTest test;

    @Before
    public void beforeScenario(Scenario scenario) {
        test = extent.createTest(scenario.getName());
    }

    @Given("I navigate to the Student Registration page")
    public void i_navigate_to_registration_page() {
        driver = Hooks.getDriver();
        driver.get(BaseClass.getBaseUrl());

        login = new loginPage(driver);
        login.setUsername(BaseClass.getUsername());
        login.setPassword(BaseClass.getPassword());
        login.clickSignIn();
        test.log(Status.INFO, "Logged in as " + BaseClass.getUsername());

        WaitUtility.waitForClickable(driver, By.id("student")).click();
        test.log(Status.INFO, "Clicked on Student menu");

        regPage = new RegistrationPage(driver);
        By regLink = By.xpath("//*[@id=\"registerLink\"]/span");
        WaitUtility.waitForClickable(driver, regLink).click();
        test.log(Status.INFO, "Navigated to Registration Page");
    }

    @When("I fill the registration form with email {string}")
    public void i_fill_registration_form_with_email(String email) {
        regPage.setStudentName("Auto Tester");
        regPage.setMobileNumber("9876543210");
        regPage.setEmailId(email);
        regPage.setCgpa("3.8");
        regPage.setDepartment("Computer Science");
        regPage.setBacklogCount("2");
        test.log(Status.INFO, "Filled form with email: " + email);
    }

    @And("I submit the registration form")
    public void i_submit_registration_form() {
        regPage.clickRegister();
        test.log(Status.INFO, "Submitted the registration form");
    }

    @Then("I should see a registration success message")
    public void i_should_see_registration_success_message() {
        Map<String, String> msg    = regPage.getDisplayedMessage();
        String            type     = msg.get("type");     // "error", "result", or "none"
        String            content  = msg.get("message");  // the actual text

        if (!"result".equals(type) || content.isEmpty()) {
            test.fail("Expected success message, but got [" + type + "]: " + content);
            throw new AssertionError("Expected registration success, but got [" + type + "]: " + content);
        } else {
            test.pass("Received success message: " + content);
        }
    }

    // ------------------------------------------------------------------------
    // Data-driven validation for invalid/blank emails
    // ------------------------------------------------------------------------

    @When("I validate email of type {string} using data from {string}")
    public void validate_email_of_type_using_data_from(String typeKey, String csvPath) {
        test.log(Status.INFO, "Loading test data for email type: " + typeKey);
        List<Map<String, String>> testCases = DataReader.readCsv(csvPath);

        Map<String, String> tc = testCases.stream()
            .filter(row -> row.get("Type").equalsIgnoreCase(typeKey))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No test case for type: " + typeKey));

        String email    = tc.get("Input");
        String expected = tc.get("ExpectedOutcome")
                            .replace("Error: ", "")
                            .trim();

        // Fill out the form
        regPage.setStudentName("Auto Tester");
        regPage.setMobileNumber("9876543210");
        regPage.setEmailId(email);
        regPage.setCgpa("3.8");
        regPage.setDepartment("Computer Science");
        regPage.setBacklogCount("2");
        regPage.clickRegister();
        test.log(Status.INFO, "Submitted form with email: " + (email.isBlank() ? "<BLANK>" : email));

        ScreenshotUtil.takeScreenshot(driver, typeKey);

        // Capture type and message
        Map<String, String> msg    = regPage.getDisplayedMessage();
        String            msgType  = msg.get("type");     // "error", "result", or "none"
        String            actual   = msg.get("message");  // the actual text

        //To log the type and actual message
        System.out.println("[ACUTAL DISPLAYED MESSAGE]"+msgType + "->" + actual);
        System.out.println("[EXPECTED MESSAGE]" + expected);

        if ("Success".equalsIgnoreCase(expected)) {
            if (!"result".equals(msgType) || actual.isEmpty()) {
                test.fail("[" + typeKey + "] FAILED: expected success but got [" + msgType + "]: '" + actual + "'");
                throw new AssertionError("Expected registration success, but got [" + msgType + "]: " + actual);
            } else {
                test.pass("[" + typeKey + "] Passed: " + actual);
            }
        } else {
            if ("error".equals(msgType) && expected.equals(actual)) {
                test.pass("[" + typeKey + "] Passed: correct error '" + actual + "'");
            } else {
                test.fail("[" + typeKey + "] FAILED: expected error '" + expected + "' but got [" + msgType + "]: '" + actual + "'");
                throw new AssertionError("Validation failed for email type: " + typeKey);
            }
        }
    }

    @Then("email validation should be asserted")
    public void email_validation_should_be_asserted() {
        test.log(Status.INFO, "Completed email validation");
    }

    @After
    public void afterScenario() {
        extent.flush();
    }
}
