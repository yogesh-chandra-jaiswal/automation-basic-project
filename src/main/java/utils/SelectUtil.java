package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.time.Duration;

/**
 * SelectUtil - Utility class for dropdown/select operations
 * Provides methods for interacting with HTML select elements
 */
public class SelectUtil {

    private WebDriver driver;
    private WebDriverWait wait;

    public SelectUtil(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Select option by visible text
     */
    public void selectByVisibleText(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            Select select = new Select(element);
            select.selectByVisibleText(text);
            LoggerUtil.info("Selected option by visible text: " + text);
        } catch (Exception e) {
            LoggerUtil.error("Failed to select option by visible text: " + text, e);
            throw e;
        }
    }

    /**
     * Select option by value
     */
    public void selectByValue(By locator, String value) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            Select select = new Select(element);
            select.selectByValue(value);
            LoggerUtil.info("Selected option by value: " + value);
        } catch (Exception e) {
            LoggerUtil.error("Failed to select option by value: " + value, e);
            throw e;
        }
    }

    /**
     * Select option by index
     */
    public void selectByIndex(By locator, int index) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            Select select = new Select(element);
            select.selectByIndex(index);
            LoggerUtil.info("Selected option by index: " + index);
        } catch (Exception e) {
            LoggerUtil.error("Failed to select option by index: " + index, e);
            throw e;
        }
    }

    /**
     * Get count of options
     */
    public int getOptionCount(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        int count = select.getOptions().size();
        LoggerUtil.info("Total options count: " + count);
        return count;
    }

    /**
     * Get all options as text
     */
    public java.util.List<String> getAllOptionsText(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        java.util.List<String> optionsList = new java.util.ArrayList<>();
        for (WebElement option : select.getOptions()) {
            optionsList.add(option.getText());
        }
        LoggerUtil.info("All options retrieved");
        return optionsList;
    }

    /**
     * Get selected option text
     */
    public String getSelectedOptionText(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        String selectedText = select.getFirstSelectedOption().getText();
        LoggerUtil.info("Selected option text: " + selectedText);
        return selectedText;
    }
}

