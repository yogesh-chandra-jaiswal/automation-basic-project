package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * WaitUtil - Utility class for explicit waits
 * Provides methods for various wait conditions
 */
public class WaitUtil {

    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtil(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Wait for element to be visible
     */
    public WebElement waitForElementToBeVisible(By locator, int seconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            LoggerUtil.info("Waiting for element to be visible: " + locator);
            return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            LoggerUtil.error("Element not visible within " + seconds + " seconds", e);
            throw e;
        }
    }

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementToBeClickable(By locator, int seconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            LoggerUtil.info("Waiting for element to be clickable: " + locator);
            return customWait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            LoggerUtil.error("Element not clickable within " + seconds + " seconds", e);
            throw e;
        }
    }

    /**
     * Wait for element to be present
     */
    public WebElement waitForElementToBePresent(By locator, int seconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            LoggerUtil.info("Waiting for element to be present: " + locator);
            return customWait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            LoggerUtil.error("Element not present within " + seconds + " seconds", e);
            throw e;
        }
    }

    /**
     * Wait for element to be invisible
     */
    public boolean waitForElementToBeInvisible(By locator, int seconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            LoggerUtil.info("Waiting for element to be invisible: " + locator);
            return customWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            LoggerUtil.error("Element still visible after " + seconds + " seconds", e);
            return false;
        }
    }

    /**
     * Wait for page title
     */
    public boolean waitForPageTitle(String title, int seconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            LoggerUtil.info("Waiting for page title: " + title);
            return customWait.until(ExpectedConditions.titleIs(title));
        } catch (Exception e) {
            LoggerUtil.error("Page title not matched within " + seconds + " seconds", e);
            return false;
        }
    }

    /**
     * Wait for URL to contain text
     */
    public boolean waitForURLContains(String text, int seconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            LoggerUtil.info("Waiting for URL to contain: " + text);
            return customWait.until(ExpectedConditions.urlContains(text));
        } catch (Exception e) {
            LoggerUtil.error("URL does not contain text within " + seconds + " seconds", e);
            return false;
        }
    }

    /**
     * Wait for number of elements to be present
     */
    public int waitForNumberOfElements(By locator, int elementCount, int seconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            LoggerUtil.info("Waiting for " + elementCount + " elements with locator: " + locator);
            return customWait.until(ExpectedConditions.numberOfElementsToBe(locator, elementCount)).size();
        } catch (Exception e) {
            LoggerUtil.error("Expected number of elements not found within " + seconds + " seconds", e);
            throw e;
        }
    }
}

