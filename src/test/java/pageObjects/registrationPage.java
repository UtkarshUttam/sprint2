package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.Map;
import java.util.HashMap;

public class RegistrationPage {

    WebDriver driver;
    //Constructor
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    
    By txt_studentName_loc = By.xpath("//input[@id='studentName']");
    By txt_mobileNumber_loc = By.xpath("//input[@id='mobileNumber']");
    By txt_emailId_loc = By.xpath("//input[@id='emailId']");
    By num_cgpa_loc = By.xpath("//input[@id='cgpa']");
    By txt_dept_loc = By.xpath("//input[@id='deptName']");
    By num_backlog_loc = By.xpath("//input[@id='backlogCount']");
    By btn_register_loc = By.xpath("//button[@id='register']");
    By btn_student_loc = By.xpath("//*[@id='student']");
    By btn_faculty_loc = By.xpath("//*[@id='faculty']");
    By btn_registerLink_loc = By.xpath("//*[@id='registerLink']");
    
    
    By error_msg = By.xpath("//*[@id='error']");
    By result_msg = By.xpath("//*[@id='result']");
    
    //Action methods
    public void setStudentName(String studentName){
        driver.findElement(txt_studentName_loc).sendKeys(studentName);
    }

    public void setMobileNumber(String mobileNumber){
        driver.findElement(txt_mobileNumber_loc).sendKeys(mobileNumber);
    }

    public void setEmailId(String emailId){
        driver.findElement(txt_emailId_loc).sendKeys(emailId);
    }

    public void setCgpa(String cgpa){
        driver.findElement(num_cgpa_loc).sendKeys(cgpa);
    }

    public void setDepartment(String department){
        driver.findElement(txt_dept_loc).sendKeys(department);
    }

    public void setBacklogCount(String backlogCount){
        driver.findElement(num_backlog_loc).sendKeys(backlogCount);
    }
    
    public void clickRegister(){
        driver.findElement(btn_register_loc).click();
    }
    public void clickStudent(){
        driver.findElement(btn_student_loc).click();
    }
    public void clickFaculty(){
        driver.findElement(btn_faculty_loc).click();
    }
    public void clickregisterLink(){
        driver.findElement(btn_registerLink_loc).click();
    }


    public Map<String, String> getDisplayedMessage() {
        Map<String, String> map = new HashMap<>();
        String errorText = "";
        String resultText = "";

        try {
            Thread.sleep(1000); // small pause for UI update

            WebElement err = driver.findElement(error_msg);
            if (err.isDisplayed()) {
                errorText = err.getText().trim();
            }

            WebElement res = driver.findElement(result_msg);
            if (res.isDisplayed()) {
                resultText = res.getText().trim();
            }

        } catch (Exception e) {
            // in case elements aren’t present or other issues
        }

        if (!errorText.isEmpty()) {
            map.put("type", "error");
            map.put("message", errorText);
        }
        else if (!resultText.isEmpty()) {
            map.put("type", "result");
            map.put("message", resultText);
        }
        else {
            map.put("type", "none");
            map.put("message", "");
        }

        return map;
    }
}