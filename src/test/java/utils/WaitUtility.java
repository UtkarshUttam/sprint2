package utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WaitUtility {

    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    // Wait until an element is visible
    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        return new WebDriverWait(driver, TIMEOUT)
            .until(ExpectedConditions.visibilityOf(element));
    }

    // Wait until an element located by By is visible
    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, TIMEOUT)
            .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait until an element is clickable
    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return new WebDriverWait(driver, TIMEOUT)
            .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Wait until an element contains specific text
    public static boolean waitForText(WebDriver driver, WebElement element, String expectedText) {
        return new WebDriverWait(driver, TIMEOUT)
            .until(ExpectedConditions.textToBePresentInElement(element, expectedText));
    }
}
