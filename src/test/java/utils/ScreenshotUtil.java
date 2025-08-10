package utils;

import org.openqa.selenium.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Base64;

public class ScreenshotUtil {

    /**
     * Takes a screenshot and returns the file path for Extent Reports
     *
     * @param driver         WebDriver instance
     * @param screenshotName Base name of the screenshot file
     * @param scrollToTable  true = scroll to table (success), false = scroll to middle (error)
     * @return String path to the screenshot file
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName, boolean scrollToTable) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            if (scrollToTable) {
                try {
                    WebElement table = driver.findElement(By.id("studTable"));
                    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", table);
                } catch (NoSuchElementException e) {
                    System.out.println("⚠️ Table not found, scrolling to middle of the page.");
                    js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
                }
            } else {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
            }

            Thread.sleep(1000); // wait for scroll to complete

            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = "screenshots/" + screenshotName + "_" + timestamp + ".png";

            File destFile = new File(filePath);
            destFile.getParentFile().mkdirs();
            Files.copy(srcFile.toPath(), destFile.toPath());

            System.out.println("📸 Screenshot saved: " + filePath);
            return filePath;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Takes a screenshot and returns it as Base64 string for embedding in reports
     */
    public static String takeScreenshotAsBase64(WebDriver driver, String screenshotName, boolean scrollToTable) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            if (scrollToTable) {
                try {
                    WebElement table = driver.findElement(By.id("studTable"));
                    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", table);
                } catch (NoSuchElementException e) {
                    System.out.println("⚠️ Table not found, scrolling to middle of the page.");
                    js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
                }
            } else {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
            }

            Thread.sleep(1000);

            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            
            return Base64.getEncoder().encodeToString(screenshot);

        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Overloaded methods with default scrollToTable = true
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        return takeScreenshot(driver, screenshotName, true);
    }

    public static String takeScreenshotAsBase64(WebDriver driver, String screenshotName) {
        return takeScreenshotAsBase64(driver, screenshotName, true);
    }
}
