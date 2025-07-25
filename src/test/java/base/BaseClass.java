package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    private static WebDriver driver;
    private static Properties prop;

    // ✅ Initializes browser and loads properties
    public static void initializeBrowser() {
        loadConfig();

        // Browser setup (expandable)
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        System.out.println("[BASE] Browser launched successfully");
    }

    // ✅ Loads properties from config file
    public static void loadConfig() {
        prop = new Properties();
        try (FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/config/config.properties")) {
            prop.load(ip);
            System.out.println("[CONFIG] Properties loaded");
        } catch (IOException e) {
            System.err.println("[ERROR] Could not load config.properties");
            e.printStackTrace();
        }
    }

    // ✅ Access WebDriver
    public static WebDriver getDriver() {
        return driver;
    }

    // ✅ Access individual properties with fallback handling
    public static String getUsername() {
        return prop.getProperty("username", "");
    }

    public static String getPassword() {
        return prop.getProperty("password", "");
    }

    public static String getBaseUrl() {
        return prop.getProperty("baseUrl", "http://webapps.tekstac.com:2121/"); // Replace with a fallback if needed
    }
}
