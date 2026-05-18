package com.base;

import com.pageObject.HomePage;
import com.pageObject.LoginPage;
import com.utilities.LoggerManager;
import com.utilities.TestContextManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import com.commonMethods.ActionDriver;
import com.driverInstance.DriverManager;
import static com.driverInstance.DriverManager.actionDriver;
import static com.driverInstance.DriverManager.getDriver;

public class BaseClass {
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected static Properties prop;
    public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
    protected ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);
    // Getter method for soft assert
    public SoftAssert getSoftAssert() {
        return softAssert.get();
    }

    @BeforeSuite
    public void loadConfig() throws IOException {
        // Load the configuration file
        prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/config.properties");
        prop.load(fis);
        logger.info("config.properties file loaded");

        // Start the Extent Report
        // ExtentManager.getReporter(); --This has been implemented in TestListener
    }
    @BeforeMethod
    @Parameters("browser")
    public synchronized void setup(String browser) throws IOException, InterruptedException {
        logger.info("Setting up WebDriver for: " + this.getClass().getSimpleName() + " on browser: " + browser);
        System.out.println("Setting up WebDriver for:" + this.getClass().getSimpleName());
        DriverManager.launchBrowser(browser);
        // Initialize ActionDriver IMMEDIATELY after launching browser
        actionDriver.set(new ActionDriver(getDriver()));
        configureBrowser();
        staticWait(2);
    }

    @BeforeMethod(dependsOnMethods = "setup")
    public void setupPages(Method method){
        String testName = method.getName();
        TestContextManager.setTestName(testName);
        System.out.println("SETTING TEST NAME: " + testName);
        logger.info("for test " + testName + " ActionDriver initialized for thread: " + Thread.currentThread().getId());
        loginPage = new LoginPage(getDriver());
        homePage  = new HomePage(getDriver());
    }
    /*
     * Configure browser settings such as implicit wait, maximize the browser and
     * navigate to the URL
     */

    private void configureBrowser() {
        // Implicit Wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        boolean seleniumGrid = Boolean.parseBoolean(System.getProperty("seleniumGrid", prop.getProperty("seleniumGrid")));
//        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // maximize the browser
//        getDriver().manage().window().maximize();

        // Navigate to URL
		/*try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the URL:" + e.getMessage());
		} */

        if (seleniumGrid) {
//            getDriver().get(prop.getProperty("url_grid"));
        } else {
//            getDriver().get(prop.getProperty("url_local"));
        }
    }

    @AfterMethod
    public synchronized void tearDown() {
        if (getDriver() != null) {
            try {
                getDriver().quit();
            } catch (Exception e) {
                System.out.println("unable to quit the driver:" + e.getMessage());
            }
           DriverManager.unLoadDriver();
        }
        logger.info("WebDriver instance is closed.");
//        actionDriver.remove();
        // driver = null;
        // actionDriver = null;
        // ExtentManager.endTest(); --This has been implemented in TestListener
    }

    /*
     *
     *
     * //Driver getter method public WebDriver getDriver() { return driver; }
     */

    // Getter Method for WebDriver


    // Getter Method for ActionDriver
//    public static ActionDriver getActionDriver() {

//        if (actionDriver.get() == null) {
//            System.out.println("ActionDriver is not initialized");
//            throw new IllegalStateException("ActionDriver is not initialized");
//        }
//        return actionDriver.get();

//    }

    // Getter method for prop
    public static Properties getProp() {
        return prop;
    }

    // Driver setter method
    public void setDriver(ThreadLocal<WebDriver> driver) {
//        this.driver = driver;
    }

    // Static wait for pause
    public void staticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }

}
