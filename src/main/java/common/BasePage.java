package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import utils.*;
import config.ConfigReader;
import java.time.Duration;
import java.util.List;

/**
 * BasePage - Base class for all page objects
 * Provides common methods for element interactions and waits
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptUtil jsUtil;
    protected WaitUtil waitUtil;
    protected ScreenshotUtil screenshotUtil;
    protected SelectUtil selectUtil;
    protected AlertUtil alertUtil;
    protected WindowHandleUtil windowHandleUtil;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int explicitWait = ConfigReader.getIntProperty("explicit.wait");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        this.jsUtil = new JavascriptUtil(driver);
        this.waitUtil = new WaitUtil(driver);
        this.screenshotUtil = new ScreenshotUtil(driver);
        this.selectUtil = new SelectUtil(driver);
        this.alertUtil = new AlertUtil(driver);
        this.windowHandleUtil = new WindowHandleUtil(driver);
        PageFactory.initElements(driver, this);
        LoggerUtil.info("BasePage initialized");
    }

      /**
     * Generic method to navigate to URL from config.properties
     * Used by all page classes - loads URL from config using property key
     */
    protected void navigateToURL(String configKey) {
        try {
            String url = ConfigReader.getProperty(configKey);
            if (url == null || url.isEmpty()) {
                throw new IllegalArgumentException("URL not found in config for key: " + configKey);
            }
            driver.get(url);
            LoggerUtil.info("Navigated to URL from config key: " + configKey + " -> " + url);
        } catch (Exception e) {
            LoggerUtil.error("Failed to navigate to URL with config key: " + configKey, e);
            throw e;
        }
    }

    /**
     * Generic method to navigate to absolute URL
     * Used when URL is hardcoded or not in config
     */
    protected void navigateTo(String url) {
        try {
            driver.get(url);
            LoggerUtil.info("Navigated to URL: " + url);
        } catch (Exception e) {
            LoggerUtil.error("Failed to navigate to URL: " + url, e);
            throw e;
        }
    }

    /**
     * Click element with explicit wait
     */
    protected void click(By locator) {
        try {
            LoggerUtil.info("Clicking element: " + locator);
            int explicitWait = ConfigReader.getIntProperty("explicit.wait");
            waitUtil.waitForElementToBeClickable(locator, explicitWait).click();
        } catch (Exception e) {
            LoggerUtil.error("Failed to click element: " + locator, e);
            throw e;
        }
    }

    /**
     * Check if element is present
     */
    protected boolean isElementPresent(By locator) {
        try {
            LoggerUtil.info("Checking if element is present: " + locator);
            int explicitWait = ConfigReader.getIntProperty("explicit.wait");
            waitUtil.waitForElementToBeVisible(locator, explicitWait);
            return true;
        } catch (Exception e) {
            LoggerUtil.warn("Element not found: " + locator);
            return false;
        }
    }

    /**
     * Check if element is visible
     */
    protected boolean isElementVisible(By locator) {
        try {
            LoggerUtil.info("Checking if element is visible: " + locator);
            return isElementPresent(locator);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is enabled
     */
    protected boolean isElementEnabled(By locator) {
        try {
            LoggerUtil.info("Checking if element is enabled: " + locator);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get list of elements
     */
    protected List<WebElement> getElements(By locator) {
        try {
            LoggerUtil.info("Getting list of elements: " + locator);
            return driver.findElements(locator);
        } catch (Exception e) {
            LoggerUtil.error("Failed to get elements", e);
            return null;
        }
    }

    /**
     * Wait for page to load
     */
    protected void waitForPageLoad() {
        try {
            LoggerUtil.info("Waiting for page to load");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            int timeout = ConfigReader.getIntProperty("page.load.timeout");
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            LoggerUtil.warn("Page load wait timed out");
        }
    }

    /**
     * Accept alert if present
     */
    protected void acceptAlertIfPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alertUtil.acceptAlert();
        } catch (Exception e) {
            LoggerUtil.info("No alert present");
        }
    }

    /**
     * Take screenshot
     */
    protected void takeScreenshot(String fileName) {
        screenshotUtil.takeScreenshot(fileName);
    }

    /**
     * Get text from element
     */
    protected String getText(By locator) {
        try {
            LoggerUtil.info("Getting text from element: " + locator);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        } catch (Exception e) {
            LoggerUtil.error("Failed to get text", e);
            return "";
        }
    }

    /**
     * Get text from WebElement
     */
    protected String getText(WebElement element) {
        try {
            LoggerUtil.info("Getting text from WebElement");
            return element.getText();
        } catch (Exception e) {
            LoggerUtil.error("Failed to get text from element", e);
            return "";
        }
    }

    /**
     * Get current URL
     */
    protected String getCurrentURL() {
        try {
            String url = driver.getCurrentUrl();
            LoggerUtil.info("Current URL: " + url);
            return url;
        } catch (Exception e) {
            LoggerUtil.error("Failed to get current URL", e);
            return "";
        }
    }

    /**
     * Check if WebElement is present
     */
    protected boolean isElementPresent(WebElement element) {
        try {
            LoggerUtil.info("Checking if WebElement is present");
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            LoggerUtil.warn("WebElement not found or not displayed");
            return false;
        }
    }

    /**
     * Send keys to element
     */
    protected void sendKeys(By locator, String text) {
        try {
            LoggerUtil.info("Sending keys to element: " + locator);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
        } catch (Exception e) {
            LoggerUtil.error("Failed to send keys", e);
            throw e;
        }
    }
}