package steps;

import config.ConfigReader;
import hooks.Hooks;
import pages.LoginPage;
import pages.InventoryPage;
import utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

/**
 * LoginSteps - Step definitions for Login feature
 * Implements BDD scenarios for login functionality
 */
public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    /**
     * Initialize page objects before each step
     */
    private void initializePageObjects() {
        this.driver = Hooks.getDriver();
        this.loginPage = new LoginPage(driver);
        this.inventoryPage = new InventoryPage(driver);
    }

    @Given("I am on the Sauce Demo login page")
    public void i_am_on_the_Sauce_Demo_login_page() {
        try {
            LoggerUtil.info("Starting: Navigate to Sauce Demo login page");
            initializePageObjects();

            loginPage.navigateToLoginPage();
            LoggerUtil.info("Navigated to login URL");

            loginPage.verifyLoginPageDisplayed();
            LoggerUtil.info("Login page elements verified");

            String pageTitle = loginPage.getPageTitle();
            if (!pageTitle.equals("Swag Labs")) {
                throw new AssertionError("Page title mismatch - Expected 'Swag Labs' but got '" + pageTitle + "'");
            }

            LoggerUtil.info("✓ Successfully on the Sauce Demo login page");
        } catch (Exception e) {
            LoggerUtil.error("Failed to navigate to login page", e);
            throw e;
        }
    }

    @When("I enter valid username and password")
    public void i_enter_valid_username_and_password() {
        try {
            LoggerUtil.info("Starting: Enter valid username and password");
            if (loginPage == null) initializePageObjects();

            String username = ConfigReader.getProperty("username");
            String password = ConfigReader.getProperty("password");

            loginPage.enterUsername(username);
            LoggerUtil.info("Entered username: " + username);

            loginPage.enterPassword(password);
            LoggerUtil.info("Entered password");

            LoggerUtil.info("✓ Successfully entered valid credentials");
        } catch (Exception e) {
            LoggerUtil.error("Failed to enter credentials", e);
            throw e;
        }
    }

    @And("I click the login button")
    public void i_click_the_login_button() {
        try {
            LoggerUtil.info("Starting: Click the login button");
            if (loginPage == null) initializePageObjects();

            loginPage.clickLoginButton();
            LoggerUtil.info("Login button clicked");

            LoggerUtil.info("✓ Successfully clicked login button");
        } catch (Exception e) {
            LoggerUtil.error("Failed to click login button", e);
            throw e;
        }
    }

    @Then("I should be redirected to the inventory page")
    public void i_should_be_redirected_to_the_inventory_page() {
        try {
            LoggerUtil.info("Starting: Verify redirection to inventory page");
            if (inventoryPage == null) initializePageObjects();

            boolean isURLCorrect = inventoryPage.verifyInventoryPageURL();
            LoggerUtil.info("URL verification: " + (isURLCorrect ? "PASS" : "FAIL"));

            if (!isURLCorrect) {
                throw new AssertionError("User not redirected to inventory page - URL verification failed");
            }

            boolean isInventoryDisplayed = inventoryPage.isInventoryPageDisplayed();
            LoggerUtil.info("Inventory page elements displayed: " + isInventoryDisplayed);

            if (!isInventoryDisplayed) {
                throw new AssertionError("Inventory list not displayed");
            }

            int itemsCount = inventoryPage.getInventoryItemsCount();
            LoggerUtil.info("Inventory items found: " + itemsCount);

            if (itemsCount <= 0) {
                throw new AssertionError("No inventory items found");
            }

            LoggerUtil.info("✓ Successfully verified inventory page with " + itemsCount + " items");
        } catch (Exception e) {
            LoggerUtil.error("Failed to verify inventory page", e);
            throw e;
        }
    }

    /**
     * Additional step definitions for extended scenarios
     */

    @When("I add first product to cart")
    public void i_add_first_product_to_cart() {
        try {
            LoggerUtil.info("Starting: Add first product to cart");
            if (inventoryPage == null) initializePageObjects();

            inventoryPage.addItemToCartByIndex(1);
            LoggerUtil.info("First product added to cart");

            LoggerUtil.info("✓ Successfully added first product to cart");
        } catch (Exception e) {
            LoggerUtil.error("Failed to add product to cart", e);
            throw e;
        }
    }

    @Then("cart should contain {int} item")
    public void cart_should_contain_item(int expectedCount) {
        try {
            LoggerUtil.info("Starting: Verify cart contains " + expectedCount + " item(s)");
            if (inventoryPage == null) initializePageObjects();

            int actualCount = inventoryPage.getCartItemsCount();
            LoggerUtil.info("Actual cart item count: " + actualCount);

            if (actualCount != expectedCount) {
                throw new AssertionError("Cart item count mismatch - Expected " + expectedCount + " but got " + actualCount);
            }

            LoggerUtil.info("✓ Cart contains correct number of items: " + actualCount);
        } catch (Exception e) {
            LoggerUtil.error("Failed to verify cart items", e);
            throw e;
        }
    }
}