package org.cartaxcheck.webdriver;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.cartaxcheck.utils.Constants.*;


public class DriverProvider extends EventFiringWebDriver {
    private static Logger logger = Logger.getLogger(DriverProvider.class);

    public enum BrowserDriver {
        FIREFOX, CHROME,
    }

    private static ClassLoader classLoader;
    private static File file;
    protected static WebDriver driver = null;
    public static WebDriverWait driverWait = null;
    private static DesiredCapabilities capabilities;
    public static String browserName;
    protected static String deviceName = null;
    protected static String platformVersion = null;


    private static final Dimension BROWSER_WINDOW_SIZE = new Dimension(1280, 1024);
    private static String platform = null;
    private static final Integer IMPLICIT_WAIT_TIMEOUT = 20;
    public static Properties properties = new Properties();

    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            driver.quit();
            logger.debug("Driver quitted");
        }
    };

    public DriverProvider() {
        super(driver);
    }

    static {
        browserName = System.getProperty(BROSER_NAME);
        logger.debug("Browser Name: " + browserName);
        getPlatform();

        InputStream fis = DriverProvider.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Platform to run scripts: " + platform);

        deviceName = System.getProperty("deviceName");
        platformVersion = System.getProperty("platformVersion");

        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
        logger.info("Execution completed");
    }

    private static void getPlatform() {
        platform = WINDOWS;
        logger.debug("platform name: " + platform);
    }


    public static WebDriver getDriver(BrowserDriver browserDriver) {
        capabilities = new DesiredCapabilities();

        try {
            switch (browserDriver) {
                case FIREFOX:
                    driver = getFirefoxDriver();
                    logger.info("Firefox Driver is executed");
                    customiseDriver();
                    break;
                case CHROME:
                    driver = getChromeDriver();
                    logger.info("Chrome Driver is executed");
                    customiseDriver();
                    break;

                default:
                    logger.error("Invalid bowser driver");
                    throw new NotImplementedException("Unknown Driver:" + browserDriver);
            }
        } catch (Exception e) {
            logger.error("Exception occured Invalid bowser driver");
            e.printStackTrace();
        }
        int driverTimeWait = StringUtils.isEmpty(getDriverWait()) ? 10 : Integer.parseInt(getDriverWait());
        driverWait = new WebDriverWait(driver, driverTimeWait);

        return driver;

    }

    private static void getResource(String driverName, String webDriverKey) {
        classLoader = DriverProvider.class.getClassLoader();
        if (platform.equalsIgnoreCase(WINDOWS)) {
            file = new File(classLoader.getResource(driverName + WINDOWS_EXTENSION).getFile());
        }
        System.setProperty(webDriverKey, file.getAbsolutePath());

    }

    private static WebDriver getChromeDriver() {
        logger.debug("in getChromeDriver method");
        ChromeOptions chromeOptions = new ChromeOptions();
        getResource(CHROME_DRIVER_FILE_PATH, CHROME_DRIVER_KEY);
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        capabilities.setBrowserName("chrome");
        capabilities.setPlatform(Platform.valueOf(platform));
        capabilities.setCapability("applicationCacheEnabled", true);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        capabilities.setCapability("acceptSslCerts", true);
        driver = new ChromeDriver(capabilities);
        return driver;
    }

    private static WebDriver getFirefoxDriver() {
        logger.debug("in getFirefoxDriver method");
        getResource(FIREFOX_DRIVER_FILE_PATH, FIREFOX_DRIVER_KEY);
        capabilities = DesiredCapabilities.firefox();
        capabilities.setBrowserName("firefox");
        capabilities.setPlatform(Platform.valueOf(platform));

        ProfilesIni profilesIni = new ProfilesIni();
        FirefoxProfile firefoxProfile = profilesIni.getProfile("default");
        firefoxProfile.setAcceptUntrustedCertificates(true);
        firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
        capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        driver = new FirefoxDriver(capabilities);
        return driver;
    }

    private static void customiseDriver() {
        driver.manage().window().setSize(BROWSER_WINDOW_SIZE);
        driver.manage().window().maximize();
    }

    public static WebDriver getWebDriver() {
        return driver;

    }

    public static String getBrowserName() {
        Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cp.getBrowserName().toLowerCase();
        logger.debug("in getBrowserName method browserName: " + browserName);
        return browserName;
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException(
                    "You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    public static String getDriverWait() {
        return properties.getProperty("driver_wait");
    }
}
