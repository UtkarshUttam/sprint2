package utils;

import org.openqa.selenium.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    /**
     * Takes a screenshot by scrolling to the student table or a fallback position.
     *
     * @param driver         WebDriver instance
     * @param screenshotName Base name of the screenshot file
     * @param scrollToTable  true = scroll to table (success), false = scroll to middle (error)
     */
    public static void takeScreenshot(WebDriver driver, String screenshotName, boolean scrollToTable) {
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
                // Scroll to middle of the page instead of top
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

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Overloaded method with default scrollToTable = true
    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        takeScreenshot(driver, screenshotName, true);
    }
}
