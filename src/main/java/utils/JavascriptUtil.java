package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

/**
 * JavascriptUtil - Utility class for executing JavaScript operations
 * Provides methods for JavaScript interactions with web elements
 */
public class JavascriptUtil {

    private static JavascriptExecutor jsExecutor;

    public JavascriptUtil(WebDriver driver) {
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    /**
     * Execute JavaScript and return result
     */
    public Object executeScript(String script, Object... args) {
        LoggerUtil.info("Executing JavaScript: " + script);
        return jsExecutor.executeScript(script, args);
    }

    /**
     * Click element using JavaScript
     */
    public void clickElementByJS(WebElement element) {
        LoggerUtil.info("Clicking element using JavaScript");
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    /**
     * Send text to element using JavaScript
     */
    public void sendKeysUsingJS(WebElement element, String text) {
        LoggerUtil.info("Sending text using JavaScript: " + text);
        jsExecutor.executeScript("arguments[0].value='" + text + "';", element);
    }

    /**
     * Get text of element using JavaScript
     */
    public String getTextUsingJS(WebElement element) {
        LoggerUtil.info("Getting text using JavaScript");
        return (String) jsExecutor.executeScript("return arguments[0].innerText;", element);
    }

    /**
     * Scroll to element
     */
    public void scrollToElement(WebElement element) {
        LoggerUtil.info("Scrolling to element");
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scroll to bottom of page
     */
    public void scrollToBottom() {
        LoggerUtil.info("Scrolling to bottom of page");
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return (String) jsExecutor.executeScript("return document.title;");
    }

    /**
     * Remove element from DOM
     */
    public void removeElement(WebElement element) {
        LoggerUtil.info("Removing element from DOM");
        jsExecutor.executeScript("arguments[0].remove();", element);
    }

    /**
     * Set attribute value
     */
    public void setAttribute(WebElement element, String attribute, String value) {
        LoggerUtil.info("Setting attribute: " + attribute + " = " + value);
        jsExecutor.executeScript("arguments[0].setAttribute('" + attribute + "','" + value + "');", element);
    }

    /**
     * Get attribute value
     */
    public String getAttribute(WebElement element, String attribute) {
        LoggerUtil.info("Getting attribute: " + attribute);
        return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attribute + "');", element);
    }
}

