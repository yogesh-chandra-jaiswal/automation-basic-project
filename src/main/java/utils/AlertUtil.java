package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * AlertUtil - Utility class for handling JavaScript alerts
 * Provides methods for accepting, dismissing and interacting with alerts
 */
public class AlertUtil {

    private WebDriver driver;
    private WebDriverWait wait;

    public AlertUtil(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /**
     * Wait for alert and accept it
     */
    public void acceptAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            LoggerUtil.info("Alert accepted");
        } catch (Exception e) {
            LoggerUtil.error("Failed to accept alert", e);
            throw e;
        }
    }

    /**
     * Wait for alert and dismiss it
     */
    public void dismissAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
            LoggerUtil.info("Alert dismissed");
        } catch (Exception e) {
            LoggerUtil.error("Failed to dismiss alert", e);
            throw e;
        }
    }

    /**
     * Get alert text
     */
    public String getAlertText() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            LoggerUtil.info("Alert text: " + alertText);
            return alertText;
        } catch (Exception e) {
            LoggerUtil.error("Failed to get alert text", e);
            return "";
        }
    }

    /**
     * Send text to alert input box
     */
    public void sendTextToAlert(String text) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.sendKeys(text);
            LoggerUtil.info("Sent text to alert: " + text);
        } catch (Exception e) {
            LoggerUtil.error("Failed to send text to alert", e);
            throw e;
        }
    }

    /**
     * Handle prompt alert
     */
    public void handlePromptAlert(String text) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.sendKeys(text);
            alert.accept();
            LoggerUtil.info("Handled prompt alert with text: " + text);
        } catch (Exception e) {
            LoggerUtil.error("Failed to handle prompt alert", e);
            throw e;
        }
    }
}

