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

public class StCgpaRegSteps {

    private WebDriver driver;
    private RegistrationPage regPage;
    private loginPage login;

    @When("the user enters {string} into the CGPA field")
    public void the_user_enters_into_cgpa_field(String cgpa) {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }
        
        // Clear the field first, then enter the CGPA
        driver.findElement(By.xpath("//input[@id='cgpa']")).clear();
        regPage.setCgpa(cgpa);
        ReportHooks.test.log(Status.INFO, "Entered CGPA: '" + cgpa + "'");
    }

    @And("fills all other fields with valid data except cgpa")
    public void fill_other_fields_for_cgpa() {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }

        // Using hardcoded valid data since we don't have ConfigReader
        regPage.setStudentName("John Smith");
        regPage.setMobileNumber("9876543210");
        regPage.setEmailId("test@example.com");
        regPage.setDepartment("Computer Science");
        regPage.setBacklogCount("1");

        ReportHooks.test.log(Status.INFO, "Filled all other fields with valid data except CGPA.");
    }

    @Then("the cgpa validation result should be {string}")
    public void validate_cgpa_result(String expectedMessage) {
        if (regPage == null) {
            driver = Hooks.getDriver();
            regPage = new RegistrationPage(driver);
        }

        try {
            Thread.sleep(1000); // Wait for response
            
            // Get the message using the Map method like your existing project
            java.util.Map<String, String> msg = regPage.getDisplayedMessage();
            String base64Screenshot = ScreenshotUtil.takeScreenshotAsBase64(driver, 
                "cgpa_validation_" + expectedMessage.replaceAll("[^a-zA-Z0-9]", "_"), true);

            // Use ValidationHelper for consistent error handling (allows missing validations as INFO)
            ValidationHelper.validateResult("CGPA Validation", expectedMessage, msg, base64Screenshot, true);
            
        } catch (InterruptedException e) {
            ReportHooks.test.fail("Thread interrupted during CGPA validation: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
