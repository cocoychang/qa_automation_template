package com.driverInstance;

import com.base.BaseClass;
import com.commonMethods.ActionDriver;
import com.utilities.ExtentManager;
import com.utilities.LoggerManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


public class DriverManager {
    protected static Properties prop;
    // Thread-safe WebDriver
    private static ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();
    public static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
    public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
    /*
     * Initialize the WebDriver based on browser defined in config.properties file
     */
    public static void launchBrowser(String browser) throws IOException, InterruptedException {
        boolean seleniumGrid = Boolean.parseBoolean(BaseClass.getProp().getProperty("seleniumGrid"));
        String gridURL = BaseClass.getProp().getProperty("gridURL");

        if (seleniumGrid) {
            try {
                if (browser.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
                    driver.set(new RemoteWebDriver(new URL(gridURL), options));
                } else if (browser.equalsIgnoreCase("firefox")) {
                    FirefoxOptions options = new FirefoxOptions();
                    options.addArguments("-headless");
                    driver.set(new RemoteWebDriver(new URL(gridURL), options));
                } else if (browser.equalsIgnoreCase("edge")) {
                    EdgeOptions options = new EdgeOptions();
                    options.addArguments("--headless=new", "--disable-gpu","--no-sandbox","--disable-dev-shm-usage");
                    driver.set(new RemoteWebDriver(new URL(gridURL), options));
                } else {
                    throw new IllegalArgumentException("Browser Not Supported: " + browser);
                }
                logger.info("RemoteWebDriver instance created for Grid in headless mode");
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Grid URL", e);
            }
        } else {

            if (browser.equalsIgnoreCase("chrome")) {

                // Create ChromeOptions
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless"); // Run Chrome in headless mode
                options.addArguments("--disable-gpu"); // Disable GPU for headless mode
                options.addArguments("--window-size=1920,1080"); // Set window size
                options.addArguments("--disable-notifications"); // Disable browser notifications
                options.addArguments("--no-sandbox"); // Required for some CI environments like Jenkins
                options.addArguments("--disable-dev-shm-usage"); // Resolve issues in resource-limited environments

                // driver = new ChromeDriver();
                driver.set(new ChromeDriver(options)); // New Changes as per Thread
                ExtentManager.registerDriver(getDriver());
                logger.info("ChromeDriver Instance is created.");
            } else if (browser.equalsIgnoreCase("firefox")) {

                // Create FirefoxOptions
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless"); // Run Firefox in headless mode
                options.addArguments("--disable-gpu"); // Disable GPU rendering (useful for headless mode)
                options.addArguments("--width=1920"); // Set browser width
                options.addArguments("--height=1080"); // Set browser height
                options.addArguments("--disable-notifications"); // Disable browser notifications
                options.addArguments("--no-sandbox"); // Needed for CI/CD environments
                options.addArguments("--disable-dev-shm-usage"); // Prevent crashes in low-resource environments

                // driver = new FirefoxDriver();
                driver.set(new FirefoxDriver(options)); // New Changes as per Thread
                ExtentManager.registerDriver(getDriver());
                logger.info("FirefoxDriver Instance is created.");
            } else if (browser.equalsIgnoreCase("edge")) {

                EdgeOptions options = new EdgeOptions();
                options.addArguments("--headless"); // Run Edge in headless mode
                options.addArguments("--disable-gpu"); // Disable GPU acceleration
                options.addArguments("--window-size=1920,1080"); // Set window size
                options.addArguments("--disable-notifications"); // Disable pop-up notifications
                options.addArguments("--no-sandbox"); // Needed for CI/CD
                options.addArguments("--disable-dev-shm-usage"); // Prevent resource-limited crashes

                // driver = new EdgeDriver();
                driver.set(new EdgeDriver(options)); // New Changes as per Thread
                ExtentManager.registerDriver(getDriver());
                logger.info("EdgeDriver Instance is created.");
            } else {
                throw new IllegalArgumentException("Browser Not Supported:" + browser);
            }
        }
        getDriver().get(BaseClass.getProp().getProperty("url_local"));
        Thread.sleep(10000);
    }
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            System.out.println("WebDriver is not initialized");
            throw new IllegalStateException("WebDriver is not initialized");
        }
        return driver.get();

    }
    // Getter Method for ActionDriver
    public static ActionDriver getActionDriver() {

        if (actionDriver.get() == null) {
            System.out.println("ActionDriver is not initialized");
            throw new IllegalStateException("ActionDriver is not initialized");
        }
        return actionDriver.get();

    }
    public static void unLoadDriver(){
        driver.remove();
        actionDriver.remove();
    }

}
