package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Set;

/**
 * WindowHandleUtil - Utility class for handling multiple windows/tabs
 * Provides methods for window and tab management
 */
public class WindowHandleUtil {

    private WebDriver driver;

    public WindowHandleUtil(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Switch to new window/tab
     */
    public void switchToNewWindow() {
        try {
            String originalWindow = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();

            for (String handle : handles) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    LoggerUtil.info("Switched to new window");
                    break;
                }
            }
        } catch (Exception e) {
            LoggerUtil.error("Failed to switch to new window", e);
            throw e;
        }
    }

    /**
     * Switch to window by title
     */
    public boolean switchToWindowByTitle(String title) {
        try {
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                driver.switchTo().window(handle);
                if (driver.getTitle().equals(title)) {
                    LoggerUtil.info("Switched to window with title: " + title);
                    return true;
                }
            }
            LoggerUtil.warn("Window with title not found: " + title);
            return false;
        } catch (Exception e) {
            LoggerUtil.error("Failed to switch to window by title", e);
            return false;
        }
    }

    /**
     * Get window count
     */
    public int getWindowCount() {
        return driver.getWindowHandles().size();
    }

    /**
     * Close all windows except main
     */
    public void closeAllWindowsExceptMain() {
        try {
            String originalWindow = driver.getWindowHandles().iterator().next();
            Set<String> handles = driver.getWindowHandles();

            for (String handle : handles) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    driver.close();
                }
            }
            driver.switchTo().window(originalWindow);
            LoggerUtil.info("Closed all windows except main");
        } catch (Exception e) {
            LoggerUtil.error("Failed to close windows", e);
        }
    }

    /**
     * Get all window handles
     */
    public Set<String> getAllWindowHandles() {
        return driver.getWindowHandles();
    }
}

