// Author : Sambhram
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

public class StNameRegSteps {

    private WebDriver driver;
    private RegistrationPage regPage;
    private loginPage login;

    @When("the user enters {string} into the student name field")
    public void the_user_enters_into_student_name(String name) {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }

        // Clear the field first, then enter the name
        driver.findElement(By.xpath("//input[@id='studentName']")).clear();
        regPage.setStudentName(name);
        ReportHooks.test.log(Status.INFO, "Entered student name: '" + name + "'");
    }

    @And("fills all other fields with valid data")
    public void fill_other_fields() {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }

        // Using hardcoded valid data since we don't have ConfigReader
        regPage.setMobileNumber("9876543210");
        regPage.setEmailId("student@example.com");
        regPage.setCgpa("3.5");
        regPage.setDepartment("Computer Science");
        regPage.setBacklogCount("1");

        ReportHooks.test.log(Status.INFO, "Filled other fields with valid data.");
    }

    @Then("the name validation result should be {string}")
    public void validate_name_result(String expectedMessage) {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }

        try {
            Thread.sleep(1000); // Wait for response
            
            // Get the message using the Map method like your existing project
            java.util.Map<String, String> msg = regPage.getDisplayedMessage();
            String base64Screenshot = ScreenshotUtil.takeScreenshotAsBase64(driver, 
                "name_validation_" + expectedMessage.replaceAll("[^a-zA-Z0-9]", "_"), true);

            // Use ValidationHelper for consistent error handling (allows missing validations as INFO)
            utils.ValidationHelper.validateResult("Name Validation", expectedMessage, msg, base64Screenshot, true);
            
        } catch (InterruptedException e) {
            ReportHooks.test.fail("Thread interrupted during validation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}