package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class loginPage {

    WebDriver driver;

    // Constructor
    public loginPage(WebDriver driver){
        this.driver = driver;
    }

    // Locators
    By txt_username_loc = By.xpath("//*[@id=\"username\"]");
    By txt_password_loc = By.xpath("//*[@id=\"password\"]");
    By btn_signIn_loc   = By.xpath("//*[@id=\"signIn\"]");

    // Placeholder locators for your updates
    By txt_passwordError_loc = By.id("error");
    // You can reuse txt_password_loc for getPasswordField()

    // Action methods
    public void setUsername(String username) {
        driver.findElement(txt_username_loc).sendKeys(username);
    }

    public void setPassword(String password){
        driver.findElement(txt_password_loc).sendKeys(password);
    }

    public void clickSignIn(){
        driver.findElement(btn_signIn_loc).click();
    }

    // Getter for password field element
    public WebElement getPasswordField() {
        return driver.findElement(txt_password_loc);
    }

    // Getter for password error element
    public WebElement getPasswordError() {
        return driver.findElement(txt_passwordError_loc);
    }
}
