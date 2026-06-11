package hooks;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import config.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import utils.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Hooks - Test lifecycle management class
 * Handles setup and teardown operations for each scenario
 */
public class Hooks {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<String> browserName = new ThreadLocal<>();

    /**
     * Get WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Clear previous reports and screenshots
     */
    @Before(order = 0)
    public void clearPreviousReports() {
        try {
            String reportPath = ConfigReader.getProperty("report.path", "target/cucumber-reports/");
            String screenshotPath = ConfigReader.getProperty("screenshot.path", "target/screenshots/");

            FileUtil.deleteDirectory(reportPath);
            FileUtil.deleteDirectory(screenshotPath);
            FileUtil.createDirectory(reportPath);
            FileUtil.createDirectory(screenshotPath);

            LoggerUtil.info("[" + Thread.currentThread().getName() + "] Previous reports and screenshots cleared");
        } catch (Exception e) {
            LoggerUtil.error("Error clearing reports: " + e.getMessage(), e);
        }
    }

    /**
     * Set up WebDriver and browser
     */
    @Before(order = 1)
    public void setUp() {
        try {
            String browser = System.getProperty("browser", ConfigReader.getProperty("browser", "chrome")).toLowerCase();
            browserName.set(browser);

            LoggerUtil.info("[" + Thread.currentThread().getName() + "] ===== Starting Test Scenario =====");
            LoggerUtil.info("[" + Thread.currentThread().getName() + "] Launching " + browser + " browser...");

            switch (browser) {
                case "chrome":
                    setupChrome();
                    break;
                case "edge":
                    setupEdge();
                    break;
                case "firefox":
                    setupFirefox();
                    break;
                default:
                    LoggerUtil.warn("Invalid browser name. Using Chrome by default.");
                    setupChrome();
            }

            // Set timeouts from config
            int implicitWait = ConfigReader.getIntProperty("implicit.wait");
            int pageLoadTimeout = ConfigReader.getIntProperty("page.load.timeout");

            driver.get().manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
            driver.get().manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
            driver.get().manage().window().maximize();

            LoggerUtil.info("[" + Thread.currentThread().getName() + "] " + browser + " browser launched successfully");
            LoggerUtil.info("[" + Thread.currentThread().getName() + "] Implicit Wait: " + implicitWait + "s, Page Load Timeout: " + pageLoadTimeout + "s");

        } catch (Exception e) {
            LoggerUtil.fatal("Failed to set up WebDriver", e);
            throw e;
        }
    }

    /**
     * Set up Chrome browser
     */
    private void setupChrome() {
        WebDriverManager.chromedriver().setup();
        driver.set(new ChromeDriver());
    }

    /**
     * Set up Edge browser
     */
    private void setupEdge() {
        WebDriverManager.edgedriver().setup();
        driver.set(new EdgeDriver());
    }

    /**
     * Set up Firefox browser
     */
    private void setupFirefox() {
        WebDriverManager.firefoxdriver().setup();
        driver.set(new FirefoxDriver());
    }

    /**
     * Take screenshot on test failure
     */
    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed() && driver.get() != null && ConfigReader.getBooleanProperty("screenshot.on.failure")) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver.get();
                File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
                String timestamp = now.format(formatter);

                String screenshotDir = ConfigReader.getProperty("screenshot.path", "target/screenshots/");
                FileUtil.createDirectory(screenshotDir);

                String screenshotName = scenario.getName().replace(" ", "_") + "_"
                    + browserName.get() + "_FAILED_" + timestamp + ".png";
                File destFile = new File(screenshotDir + File.separator + screenshotName);
                FileUtils.copyFile(srcFile, destFile);

                LoggerUtil.error("[" + Thread.currentThread().getName() + "] Test FAILED - Screenshot: " + screenshotName);

            } catch (Exception e) {
                LoggerUtil.error("Failed to take screenshot on test failure", e);
            }
        } else if (!scenario.isFailed()) {
            LoggerUtil.info("[" + Thread.currentThread().getName() + "] Test PASSED - " + scenario.getName());
        }
    }

    /**
     * Close WebDriver and cleanup
     */
    @After(order = 2)
    public void tearDown() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
                LoggerUtil.info("[" + Thread.currentThread().getName() + "] " + browserName.get() + " browser closed successfully");
            }
        } catch (Exception e) {
            LoggerUtil.error("Error closing browser", e);
        } finally {
            driver.remove();
            browserName.remove();
            LoggerUtil.info("[" + Thread.currentThread().getName() + "] ===== Test Scenario Completed =====\n");
        }
    }
}