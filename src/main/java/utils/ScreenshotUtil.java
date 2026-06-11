package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ScreenshotUtil - Utility class for taking screenshots
 * Provides methods for capturing and storing screenshots
 */
public class ScreenshotUtil {
    
    private WebDriver driver;
    
    public ScreenshotUtil(WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * Take screenshot with default naming
     */
    public String takeScreenshot() {
        try {
            String screenshotPath = getScreenshotPath("screenshot");
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(screenshotPath));
            LoggerUtil.info("Screenshot taken: " + screenshotPath);
            return screenshotPath;
        } catch (Exception e) {
            LoggerUtil.error("Failed to take screenshot", e);
            return "";
        }
    }
    
    /**
     * Take screenshot with custom name
     */
    public String takeScreenshot(String screenshotName) {
        try {
            String screenshotPath = getScreenshotPath(screenshotName);
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(screenshotPath));
            LoggerUtil.info("Screenshot taken: " + screenshotPath);
            return screenshotPath;
        } catch (Exception e) {
            LoggerUtil.error("Failed to take screenshot: " + screenshotName, e);
            return "";
        }
    }
    
    /**
     * Get screenshot path with timestamp
     */
    private String getScreenshotPath(String fileName) {
        String screenshotDir = config.ConfigReader.getProperty("screenshot.path", "target/screenshots/");
        FileUtil.createDirectory(screenshotDir);
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
        String timestamp = now.format(formatter);
        
        return screenshotDir + fileName + "_" + timestamp + ".png";
    }
    
    /**
     * Take screenshot for failed test
     */
    public String takeScreenshotOnFailure(String testName) {
        try {
            String screenshotPath = getScreenshotPath(testName + "_FAILED");
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(screenshotPath));
            LoggerUtil.error("Screenshot taken for failed test: " + screenshotPath);
            return screenshotPath;
        } catch (Exception e) {
            LoggerUtil.error("Failed to take screenshot on failure", e);
            return "";
        }
    }
}

