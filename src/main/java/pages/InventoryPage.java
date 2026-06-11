package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.LoggerUtil;
import common.BasePage;

/**
 * InventoryPage - Page Object Model for Inventory page
 * Contains all inventory page elements and actions
 */
public class InventoryPage extends BasePage {

    // Locators
    private By inventoryList = By.className("inventory_list");
    private By inventoryItem = By.className("inventory_item");
    private By productName = By.className("inventory_item_name");
    private By productPrice = By.className("inventory_item_price");
    private By addToCartButton = By.xpath("//button[contains(text(), 'Add to cart')]");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        LoggerUtil.info("InventoryPage initialized");
    }

    /**
     * Check if inventory page is displayed
     */
    public boolean isInventoryPageDisplayed() {
        LoggerUtil.info("Checking if inventory page is displayed");
        return isElementPresent(inventoryList);
    }

    /**
     * Get current URL
     */
    public String getCurrentPageURL() {
        String url = getCurrentURL();
        LoggerUtil.info("Current Page URL: " + url);
        return url;
    }

    /**
     * Verify inventory page URL
     */
    public boolean verifyInventoryPageURL() {
        LoggerUtil.info("Verifying inventory page URL");
        String url = getCurrentURL();
        return url.contains("inventory");
    }

    /**
     * Get inventory items count
     */
    public int getInventoryItemsCount() {
        LoggerUtil.info("Getting inventory items count");
        return getElements(inventoryItem).size();
    }

    /**
     * Add item to cart by index
     */
    public void addItemToCartByIndex(int index) {
        LoggerUtil.info("Adding item to cart at index: " + index);
        if (index > 0 && index <= getInventoryItemsCount()) {
            getElements(addToCartButton).get(index - 1).click();
            LoggerUtil.info("Item added to cart");
        } else {
            LoggerUtil.error("Invalid item index");
        }
    }

    /**
     * Check if cart has items
     */
    public boolean isCartHasItems() {
        LoggerUtil.info("Checking if cart has items");
        return isElementPresent(cartBadge);
    }

    /**
     * Get cart items count
     */
    public int getCartItemsCount() {
        LoggerUtil.info("Getting cart items count");
        if (isCartHasItems()) {
            String count = getText(cartBadge);
            return Integer.parseInt(count);
        }
        return 0;
    }

    /**
     * Go to cart
     */
    public void goToCart() {
        LoggerUtil.info("Going to cart");
        click(cartLink);
    }

    /**
     * Get first product name
     */
    public String getFirstProductName() {
        LoggerUtil.info("Getting first product name");
        return getText(productName);
    }

    /**
     * Get first product price
     */
    public String getFirstProductPrice() {
        LoggerUtil.info("Getting first product price");
        return getText(productPrice);
    }
}

