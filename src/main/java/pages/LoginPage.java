package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import config.ConfigReader;
import utils.LoggerUtil;
import common.BasePage;

/**
 * LoginPage - Page Object Model for Login page
 * Contains all login page elements and actions
 */
public class LoginPage extends BasePage {

    // Locators
    @FindBy(xpath = "//*[@id='user-name']")
    WebElement usernameInput;
    @FindBy(xpath = "//*[@id='password']")
    WebElement passwordInput;
    @FindBy(xpath = "//*[@id='login-button']")
    WebElement loginButton;
    @FindBy(xpath = "//div[@class='login_logo']")
    WebElement loginLogo;
    @FindBy(xpath = "//h3[@data-test='error']")
    WebElement errorMessage;


    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        LoggerUtil.info("LoginPage initialized");
    }

    /**
     * Navigate to login page
     * Uses generic navigateToURL method from BasePage with config property key
     */
    public void navigateToLoginPage() {
        LoggerUtil.info("Navigating to login page");
        navigateToURL("app.url");
        waitForPageLoad();
    }

    /**
     * Verify login page is displayed
     */
    public void verifyLoginPageDisplayed() {
        LoggerUtil.info("Verifying login page is displayed");
        try {
            if (loginLogo == null || !loginLogo.isDisplayed()) {
                throw new AssertionError("Login page is not displayed - login logo not found");
            }
            LoggerUtil.info("Login page is displayed successfully");
        } catch (Exception e) {
            throw new AssertionError("Login page verification failed: " + e.getMessage());
        }
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        LoggerUtil.info("Current page title: " + title);
        return title;
    }

    /**
     * Enter username in username field
     */
    public void enterUsername(String username) {
        LoggerUtil.info("Entering username: " + username);
        if (usernameInput == null) {
            usernameInput = driver.findElement(By.xpath("//*[@id='user-name']"));
        }
        usernameInput.clear();
        usernameInput.sendKeys(username);
        LoggerUtil.info("Username entered successfully");
    }

    /**
     * Enter password in password field
     */
    public void enterPassword(String password) {
        LoggerUtil.info("Entering password");
        if (passwordInput == null) {
            passwordInput = driver.findElement(By.xpath("//*[@id='password']"));
        }
        passwordInput.clear();
        passwordInput.sendKeys(password);
        LoggerUtil.info("Password entered successfully");
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        LoggerUtil.info("Clicking login button");
        if (loginButton == null) {
            loginButton = driver.findElement(By.xpath("//*[@id='login-button']"));
        }
        loginButton.click();
        LoggerUtil.info("Login button clicked successfully");
    }

    /**
     * Complete login with username and password
     */
    public void login(String username, String password) {
        LoggerUtil.info("Performing login with credentials");
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Get error message
     */
    public String getErrorMessage() {
        LoggerUtil.info("Getting error message");
        try {
            if (errorMessage != null && errorMessage.isDisplayed()) {
                return getText(errorMessage);
            }
        } catch (Exception e) {
            LoggerUtil.warn("Error message not found");
        }
        return "";
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        LoggerUtil.info("Checking if error message is displayed");
        try {
            return errorMessage != null && errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}