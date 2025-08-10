package utils;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ScreenshotTest {
    
    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;
    
    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        extent = ExtentManager.getInstance();
        test = extent.createTest("Screenshot Test");
    }
    
    @Test
    public void testScreenshotCapture() {
        try {
            driver.get("https://www.google.com");
            
            String base64Screenshot = ScreenshotUtil.takeScreenshotAsBase64(driver, "google_homepage");
            
            if (base64Screenshot != null && !base64Screenshot.isEmpty()) {
                test.pass("Screenshot captured successfully", 
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                System.out.println("✅ Screenshot test passed - Base64 length: " + base64Screenshot.length());
            } else {
                test.fail("Failed to capture screenshot");
                System.out.println("❌ Screenshot test failed - No Base64 data");
            }
            
        } catch (Exception e) {
            test.fail("Exception during screenshot test: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        if (extent != null) {
            extent.flush();
        }
    }
}