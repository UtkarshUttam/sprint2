// package pageObjects;

// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.By;

// public class viewAllStudentPage {

//     WebDriver driver;

//     public viewAllStudentPage(WebDriver driver) {
//         this.driver = driver;
//     }

//     // Locators
//     @FindBy(id="viewTable"); // Replace with actual ID or XPath
//     private WebElement studentTable;

//     // Row locator (generic)
//     public WebElement getRowByRollNo(String rollNo) {
//         return driver.findElement(By.xpath("//table[@id='viewTable']//tr[td[text()='" + rollNo + "']]"));
//     }

//     // Column cell locators (parameterized per row)
//     public String getNameByRollNo(String rollNo) {
//         return getRowByRollNo(rollNo).findElement(By.xpath("./td[2]")).getText();
//     }

//     public String getMobileByRollNo(String rollNo) {
//         return getRowByRollNo(rollNo).findElement(By.xpath("./td[3]")).getText();
//     }

//     public String getEmailByRollNo(String rollNo) {
//         return getRowByRollNo(rollNo).findElement(By.xpath("./td[4]")).getText();
//     }

//     public String getCGPAByRollNo(String rollNo) {
//         return getRowByRollNo(rollNo).findElement(By.xpath("./td[5]")).getText();
//     }

//     public String getDepartmentByRollNo(String rollNo) {
//         return getRowByRollNo(rollNo).findElement(By.xpath("./td[6]")).getText();
//     }

//     public String getBacklogCountByRollNo(String rollNo) {
//         return getRowByRollNo(rollNo).findElement(By.xpath("./td[7]")).getText();
//     }

//     public String getStatusByRollNo(String rollNo) {
//         return getRowByRollNo(rollNo).findElement(By.xpath("./td[8]")).getText();
//     }

//     // Utility method to verify student presence
//     public boolean isStudentRowPresent(String rollNo) {
//         try {
//             return getRowByRollNo(rollNo).isDisplayed();
//         } catch (NoSuchElementException e) {
//             return false;
//         }
//     }

// }



package pageObjects;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class viewAllStudentPage {

    WebDriver driver;

    public viewAllStudentPage(WebDriver driver) {
        this.driver = driver;
    }

    // Row locator
    public WebElement getRowByRollNo(String rollNo) {
        return driver.findElement(By.xpath("//table[@id='viewTable']//tr[td[text()='" + rollNo + "']]"));
    }

    // Return all cell data in a list (except roll number)
    public List<String> getStudentDetailsByRollNo(String rollNo) {
        List<String> studentDetails = new ArrayList<>();

        try {
            WebElement row = getRowByRollNo(rollNo);
            List<WebElement> cells = row.findElements(By.tagName("td"));

            for (WebElement cell : cells) {
                //Syystem.out.println("Cell text: " + cell.getText().trim());
                studentDetails.add(cell.getText().trim());
            }

        } catch (NoSuchElementException e) {
            System.out.println("Row not found for roll number: " + rollNo);
        }

        return studentDetails;
    }

    // Utility to confirm row presence
    public boolean isStudentRowPresent(String rollNo) {
        try {
            return getRowByRollNo(rollNo).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
