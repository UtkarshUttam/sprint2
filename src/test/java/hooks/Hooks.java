package hooks;

import io.cucumber.java.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.After;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import base.BaseClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class Hooks {
    public static WebDriver driver;

    @Before
    public void setup(Scenario scenario) {
        BaseClass.loadConfig(); // ✅ Load config before using getBaseUrl()
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    public static WebDriver getDriver() {
        return driver;
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) Hooks.getDriver();
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
            System.out.println("[HOOK] Screenshot captured for failed scenario");
        }
        Hooks.getDriver().quit();
    }
}
