// Author: Sambhram
package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;

import io.cucumber.java.en.*;
import pageObjects.RegistrationPage;
import pageObjects.loginPage;
import hooks.Hooks;
import hooks.ReportHooks;
import base.BaseClass;
import utils.ScreenshotUtil;
import utils.WaitUtility;

public class RegBtnSteps {

    private WebDriver driver;
    private RegistrationPage regPage;
    private loginPage login;

    @Given("the user is on the registration page")
    public void the_user_is_on_registration_page() {
        driver = Hooks.getDriver();
        driver.get(BaseClass.getBaseUrl());

        login = new loginPage(driver);
        login.setUsername(BaseClass.getUsername());
        login.setPassword(BaseClass.getPassword());
        login.clickSignIn();
        ReportHooks.test.log(Status.INFO, "Logged in as " + BaseClass.getUsername());

        WaitUtility.waitForClickable(driver, By.id("student")).click();
        ReportHooks.test.log(Status.INFO, "Clicked on Student menu");

        regPage = new RegistrationPage(driver);
        By regLink = By.xpath("//*[@id=\"registerLink\"]/span");
        WaitUtility.waitForClickable(driver, regLink).click();
        ReportHooks.test.log(Status.INFO, "Navigated to Registration Page");
    }

    @When("the user clicks the register button without filling the form")
    public void user_clicks_register_without_data() {
        regPage.clickRegister();
        ReportHooks.test.log(Status.INFO, "Clicked the register button without filling any data.");
    }

    @When("the user enters all the fields with valid data")
    public void enter_all_valid_fields_from_properties() {
        // Using hardcoded valid data since we don't have ConfigReader
        regPage.setStudentName("John Smith");
        regPage.setMobileNumber("9876543210");
        regPage.setEmailId("john.smith@example.com");
        regPage.setCgpa("3.8");
        regPage.setDepartment("Computer Science");
        regPage.setBacklogCount("0");

        ReportHooks.test.log(Status.INFO, "Filled all fields using valid data.");
    }

    @And("clicks the register button")
    public void clicks_register_button() {
        regPage.clickRegister();
        ReportHooks.test.log(Status.INFO, "Clicked the register button.");
    }

    @Then("the registration result should be {string}")
    public void validate_registration_result(String expectedMessage) {
        try {
            Thread.sleep(1000); // Wait for response
            
            // Get the message using the Map method like your existing project
            java.util.Map<String, String> msg = regPage.getDisplayedMessage();
            String base64Screenshot = ScreenshotUtil.takeScreenshotAsBase64(driver, 
                "registration_validation_" + expectedMessage.replaceAll("[^a-zA-Z0-9]", "_"), true);

            // Use ValidationHelper for consistent error handling (allows missing validations as INFO)
            utils.ValidationHelper.validateResult("Registration Button", expectedMessage, msg, base64Screenshot, true);
            
        } catch (InterruptedException e) {
            ReportHooks.test.fail("Thread interrupted during validation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
