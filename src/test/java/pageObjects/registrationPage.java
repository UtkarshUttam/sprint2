package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class registrationPage {

    WebDriver driver;
    //Constructor
    public registrationPage(WebDriver driver) {
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
    

    //Action methods
    public void setStudentName(String studentName){
        driver.findElement(txt_studentName_loc).sendKeys(studentName);
    }

    public void setMobileNumber(String mobileNumber){
        driver.findElement(txt_mobileNumber_loc).sendKeys(mobileNumber);
    }

    public void setEmailId(String emailId){
        driver.findElement(txt_emailId_loc).sendKeys(emailId)
    }

    public void setCgpa(string cgpa){
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
}


