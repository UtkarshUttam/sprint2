//Author: Aman
package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import com.aventstack.extentreports.Status;

import io.cucumber.java.en.*;
import pageObjects.RegistrationPage;
import pageObjects.loginPage;
import hooks.Hooks;
import hooks.ReportHooks;
import base.BaseClass;
import utils.ScreenshotUtil;
import utils.WaitUtility;
import utils.ValidationHelper;

public class StBacklogRegSteps {

    private WebDriver driver;
    private RegistrationPage regPage;
    private loginPage login;

    @Given("the user is on the registration page for backlog")
    public void the_user_is_on_registration_page_for_backlog() {
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
        ReportHooks.test.log(Status.INFO, "Navigated to Registration Page for backlog validation");
    }

    @When("the user enters {string} into the Backlog field")
    public void the_user_enters_into_backlog_field(String backlog) {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }
        
        // Clear the field first, then enter the backlog count
        driver.findElement(By.xpath("//input[@id='backlogCount']")).clear();
        regPage.setBacklogCount(backlog);
        ReportHooks.test.log(Status.INFO, "Entered Backlog count: '" + backlog + "'");
    }

    @And("fills all other fields with valid data except backlog")
    public void fill_other_fields_except_backlog() {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }

        // Using hardcoded valid data since we don't have ConfigReader
        regPage.setStudentName("John Smith");
        regPage.setMobileNumber("9876543210");
        regPage.setEmailId("test@example.com");
        regPage.setDepartment("Computer Science");
        regPage.setCgpa("8.5");

        ReportHooks.test.log(Status.INFO, "Filled all other fields with valid data except backlog.");
    }

    @And("clicks the register button for backlog")
    public void clicks_register_button_for_backlog() {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }
        
        regPage.clickRegister();
        ReportHooks.test.log(Status.INFO, "Clicked the register button for backlog validation.");
    }

    @Then("the result for backlog should be {string}")
    public void validate_backlog_result(String expectedMessage) {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }

        try {
            Thread.sleep(1000); // Wait for response
            
            // Get the message using the Map method like your existing project
            java.util.Map<String, String> msg = regPage.getDisplayedMessage();
            String base64Screenshot = ScreenshotUtil.takeScreenshotAsBase64(driver, 
                "backlog_validation_" + expectedMessage.replaceAll("[^a-zA-Z0-9]", "_"), true);

            // Use ValidationHelper for consistent error handling (allows missing validations as INFO)
            ValidationHelper.validateResult("Backlog Validation", expectedMessage, msg, base64Screenshot, true);
            
        } catch (InterruptedException e) {
            ReportHooks.test.fail("Thread interrupted during backlog validation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
