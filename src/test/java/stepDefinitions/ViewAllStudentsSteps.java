// Author: Utkarsh
package stepDefinitions;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import base.BaseClass;
import hooks.Hooks;
import hooks.ReportHooks;
import pageObjects.loginPage;
import pageObjects.viewAllStudentPage;
import utils.DataReader;
import utils.ScreenshotUtil;
import utils.WaitUtility;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ViewAllStudentsSteps {

    private WebDriver driver;
    private loginPage login;
    private viewAllStudentPage viewPage;
    private List<Map<String,String>> studentData;

    @Given("I navigate to the View All Students page")
    public void i_navigate_to_view_all_students_page() {
        driver = Hooks.getDriver();

        driver.get(BaseClass.getBaseUrl());
        login = new loginPage(driver);
        login.setUsername(BaseClass.getUsername());
        login.setPassword(BaseClass.getPassword());
        login.clickSignIn();
        ReportHooks.test.log(Status.INFO, "Logged in as " + BaseClass.getUsername());

        WaitUtility.waitForClickable(driver, By.id("student")).click();
        ReportHooks.test.log(Status.INFO, "Clicked on Student menu");

        By viewAll = By.xpath("//span[normalize-space()='View All Student']");
        WaitUtility.waitForClickable(driver, viewAll).click();
        ReportHooks.test.log(Status.INFO, "Navigated to View All Students page");

        viewPage = new viewAllStudentPage(driver);
    }

    @Given("student data is loaded from {string}")
    public void student_data_is_loaded_from(String csvFile) {
        String fullPath = "src/test/resources/data/" + csvFile;
        studentData = DataReader.readCsv(fullPath);
        ReportHooks.test.log(Status.INFO, "Loaded " + studentData.size() + " records from " + csvFile);
    }

    @When("the user validates each student by roll number")
    public void the_user_validates_each_student_by_roll_number() {
        for (Map<String,String> student : studentData) {
            String rollNo          = student.get("rollNumber").trim();
            boolean expectedExists = Boolean.parseBoolean(student.get("true").trim());

            ReportHooks.test.log(Status.INFO, String.format("Roll %s → expectedPresent=%s", rollNo, expectedExists));

            boolean actualPresent = viewPage.isStudentRowPresent(rollNo);
            ReportHooks.test.log(Status.INFO, String.format("Roll %s → actualPresent=%s", rollNo, actualPresent));

            // scroll to row if present
            if (actualPresent) {
                WebElement rowElem = viewPage.getRowByRollNo(rollNo);
                ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", rowElem);
                ReportHooks.test.info("Scrolled to row for roll number: " + rollNo);
            }
            ScreenshotUtil.takeScreenshot(driver, "RowPresence_" + rollNo);

            // expected to exist
            if (expectedExists) {
                if (!actualPresent) {
                    ReportHooks.test.fail("Expected row for roll " + rollNo + " but none found");
                    Assert.fail("Row missing for expected roll number: " + rollNo);
                }
                ReportHooks.test.pass("Row found for roll number: " + rollNo);

                // validate details
                List<String> actual = viewPage.getStudentDetailsByRollNo(rollNo);
                ReportHooks.test.info("Extracted details: " + actual);

                // scroll details into view
                WebElement rowElem = viewPage.getRowByRollNo(rollNo);
                ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", rowElem);
                ReportHooks.test.info("Scrolled to details for roll number: " + rollNo);
                ScreenshotUtil.takeScreenshot(driver, "Details_" + rollNo);

                List<String> expected = Arrays.asList(
                    rollNo,
                    student.get("name").trim(),
                    student.get("mobileNo").trim(),
                    student.get("email").trim(),
                    student.get("cgpa").trim(),
                    student.get("department").trim(),
                    student.get("backlogCount").trim()
                );

                for (int i = 0; i < expected.size(); i++) {
                    String exp = expected.get(i);
                    String act = actual.get(i).trim();
                    Assert.assertEquals(act, exp,
                        String.format("Mismatch for roll %s at column[%d]: expected [%s] but found [%s]",
                                      rollNo, i, exp, act)
                    );
                    ReportHooks.test.pass(String.format("Verified column[%d] for roll %s: [%s]", i, rollNo, act));
                }
            }
            // expected NOT to exist
            else {
                if (actualPresent) {
                    ReportHooks.test.fail("Expected NO row for roll " + rollNo + " but found one");
                    Assert.fail("Unexpected row found for roll number: " + rollNo);
                }
                ReportHooks.test.pass("No row found for roll number (as expected): " + rollNo);
            }
        }
    }

    @Then("all student details should match the expected values")
    public void all_student_details_should_match_the_expected_values() {
        ReportHooks.test.log(Status.PASS, "All student row‐existence and details validated.");
    }
}
