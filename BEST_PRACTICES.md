# Automation Framework - Best Practices Guide

## 1. Code Organization

### ✅ DO
```java
// Organize code in appropriate packages
common/              // Shared base classes
config/              // Configuration management
hooks/               // Test lifecycle management
pages/               // Page Object Model classes
steps/               // Step definitions
utils/               // Utility functions
```

### ❌ DON'T
```java
// Don't mix different concerns in single package
// Don't hardcode values in test code
// Don't create deep nested package structures
```

---

## 2. Page Object Model (POM)

### ✅ DO
```java
public class LoginPage extends BasePage {
    // Locators at top
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-btn");
    
    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    // User-centric methods
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    // Private methods for internal operations
    private void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
}
```

### ❌ DON'T
```java
// Don't use WebDriver directly in test classes
public class LoginTest {
    public void testLogin() {
        WebDriver driver = new ChromeDriver();
        driver.findElement(By.id("username")).sendKeys("user");
        // Bad practice!
    }
}

// Don't create methods that return elements
public WebElement getUsernameField() {
    return driver.findElement(By.id("username"));
}

// Don't use generic method names
public void click_1() { }
public void enter_text_field() { }
```

---

## 3. Step Definitions

### ✅ DO
```java
@Given("User is on login page")
public void user_is_on_login_page() {
    initializePageObjects();
    LoggerUtil.info("Navigating to login page");
    loginPage.navigateToLoginPage();
    loginPage.verifyLoginPageIsDisplayed();
}

@When("User logs in with valid credentials")
public void user_logs_in_with_valid_credentials() {
    String username = ConfigReader.getProperty("username");
    String password = ConfigReader.getProperty("password");
    loginPage.login(username, password);
}

@Then("User should see inventory page")
public void user_should_see_inventory_page() {
    Assert.assertTrue(inventoryPage.isInventoryPageDisplayed());
    LoggerUtil.info("Inventory page verified");
}
```

### ❌ DON'T
```java
// Don't include technical implementation details in steps
@When("User enters email and password")
public void user_enters_credentials() {
    driver.findElement(By.id("email")).sendKeys("test@example.com");
    Thread.sleep(1000); // Don't use sleep!
    driver.findElement(By.id("password")).sendKeys("password");
}

// Don't create multiple similar methods
@When("User clicks button 1") { }
@When("User clicks button 2") { }

// Don't mix multiple assertions in one step
@Then("Everything works")
public void everything_works() {
    Assert.assertTrue(condition1);
    Assert.assertTrue(condition2);
    Assert.assertTrue(condition3);
}
```

---

## 4. Waits and Timeouts

### ✅ DO
```java
// Use explicit waits in BasePage
protected WebElement waitForElement(By locator) {
    int timeout = ConfigReader.getIntProperty("explicit.wait");
    return new WebDriverWait(driver, Duration.ofSeconds(timeout))
        .until(ExpectedConditions.visibilityOfElementLocated(locator));
}

// Use WaitUtil for complex conditions
waitUtil.waitForElementToBeClickable(submitButton, 15);
waitUtil.waitForURLContains("/dashboard", 20);
```

### ❌ DON'T
```java
// Don't use Thread.sleep()
Thread.sleep(5000); // ❌ Hard to maintain, flaky tests

// Don't mix implicit and explicit waits extensively
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
new WebDriverWait(driver, Duration.ofSeconds(20))...

// Don't use high timeouts for all operations
new WebDriverWait(driver, Duration.ofSeconds(300))...
```

---

## 5. Assertions and Validations

### ✅ DO
```java
// Use clear, descriptive assertions
Assert.assertEquals(actualValue, expectedValue, "User balance should equal 1000");
Assert.assertTrue(isElementVisible, "Login button should be visible");
Assert.assertNotNull(userData, "User data should not be null");

// Use try-catch for better error handling
try {
    Assert.assertTrue(condition, "Specific error message");
} catch (AssertionError e) {
    LoggerUtil.error("Assertion failed", e);
    screenshotUtil.takeScreenshot("assertion-failure");
    throw e;
}
```

### ❌ DON'T
```java
// Don't use generic assertion messages
Assert.assertTrue(result); // What does result mean?

// Don't chain multiple assertions without stopping on failure
Assert.assertTrue(a);
Assert.assertTrue(b);
Assert.assertTrue(c);
// If first fails, others won't execute

// Don't use JUnit assertions when TestNG is configured
assertEquals(a, b); // Use Assert.assertEquals instead
```

---

## 6. Logging

### ✅ DO
```java
// Log at appropriate levels
LoggerUtil.info("Starting test execution for: " + testName);
LoggerUtil.debug("Element found: " + locator);
LoggerUtil.warn("Retrying operation due to timeout");
LoggerUtil.error("Test failed with exception", exception);

// Include context in logs
LoggerUtil.info("User: " + username + " attempting login");
LoggerUtil.debug("Current URL: " + driver.getCurrentUrl());
```

### ❌ DON'T
```java
// Don't use System.out.println()
System.out.println("Test started"); // Use LoggerUtil instead

// Don't log sensitive data
LoggerUtil.info("Password: " + password); // ❌ Security risk

// Don't log too verbosely
LoggerUtil.info("Step 1");
LoggerUtil.info("Step 2");
LoggerUtil.info("Step 3");
```

---

## 7. Configuration Management

### ✅ DO
```java
// Always read from ConfigReader
String url = ConfigReader.getProperty("app.url");
int timeout = ConfigReader.getIntProperty("explicit.wait");
boolean screenshot = ConfigReader.getBooleanProperty("screenshot.on.failure");

// Use default values for optional configs
String proxy = ConfigReader.getProperty("proxy.url", "none");
```

### ❌ DON'T
```java
// Don't hardcode values
driver.get("https://www.saucedemo.com/"); // ❌ Hardcoded URL

// Don't mix configurations
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
// Use configured timeout instead

// Don't use environment-specific values in code
if (System.getProperty("os.name").contains("Windows")) {
    // ❌ OS-specific code belongs in config
}
```

---

## 8. Error Handling

### ✅ DO
```java
// Handle exceptions gracefully
try {
    element.click();
} catch (StaleElementReferenceException e) {
    LoggerUtil.warn("Stale element, retrying...", e);
    element = driver.findElement(locator);
    element.click();
} catch (TimeoutException e) {
    LoggerUtil.error("Element not found within timeout", e);
    screenshotUtil.takeScreenshot("timeout-error");
    throw e;
}

// Use custom exceptions for clarity
if (element == null) {
    throw new IllegalStateException("Required element not found: " + locator);
}
```

### ❌ DON'T
```java
// Don't suppress exceptions silently
try {
    element.click();
} catch (Exception e) {
    // ❌ Silently ignoring error
}

// Don't catch generic Exception
try {
    element.click();
} catch (Exception e) {
    // Handle all exceptions the same way
}

// Don't throw without logging
if (!condition) {
    throw new RuntimeException("Error occurred"); // Missing context
}
```

---

## 9. Test Data Management

### ✅ DO
```java
// Use external data sources
String username = ConfigReader.getProperty("username");
String[] testData = readFromExcelFile("testdata.xlsx");
Map<String, String> userData = getFromDatabase("users", id);

// Create builder pattern for test data
TestUser user = new TestUserBuilder()
    .withUsername("testuser")
    .withPassword("securepass")
    .withEmail("test@example.com")
    .build();
```

### ❌ DON'T
```java
// Don't hardcode test data in tests
String username = "admin";
String password = "admin123";
String email = "admin@test.com";

// Don't use production data for testing
String realUserEmail = "john.doe@company.com"; // ❌ Privacy risk

// Don't mix test data with test logic
public void testLogin() {
    String user = "testuser";
    String pass = "testpass";
    // Test logic here
}
```

---

## 10. Parallel Execution

### ✅ DO
```java
// Use ThreadLocal for driver management
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

// Ensure cleanup in tearDown
@After
public void tearDown() {
    if (driver.get() != null) {
        driver.get().quit();
    }
    driver.remove(); // ✅ Remove to prevent memory leak
}

// Use thread-safe utility classes
Thread-safe logging
Thread-safe screenshot capture
Thread-safe report generation
```

### ❌ DON'T
```java
// Don't use static driver instance
public static WebDriver driver; // ❌ Not thread-safe

// Don't share resources between threads
private WebDriver driver; // Thread-unsafe in parallel execution

// Don't forget to clean up
@After
public void tearDown() {
    driver.quit(); // Don't remove from ThreadLocal!
}
```

---

## 11. Maintenance Guidelines

### ✅ DO
```java
// Use meaningful names
By loginButton = By.id("btn-login");
public void performSuccessfulLogin() { }

// Keep methods small and focused
public void login(String username, String password) {
    enterUsername(username);
    enterPassword(password);
    clickLoginButton();
}

// Document complex logic
/**
 * Logs in the user and verifies landing on dashboard
 * @param username User email
 * @param password User password
 * @throws LoginException if login fails
 */
public void login(String username, String password) { }
```

### ❌ DON'T
```java
// Don't use cryptic names
By btn = By.id("button");
public void lgnChk() { }

// Don't create God methods
public void doEverything() {
    login();
    clickButtons();
    fillForms();
    submitData();
    // ... 100 more lines
}

// Don't leave commented code
// WebDriver driver = new ChromeDriver();
// driver.get("url");
// element.click();
```

---

## 12. Performance Best Practices

### ✅ DO
```java
// Minimize browser interactions
public void loginAndNavigate() {
    login(username, password);
    navigateToDashboard();
    // Single browser sequence
}

// Use page caching where applicable
private InventoryPage inventoryPage;
public void reuseInventoryPage() {
    if (inventoryPage == null) {
        inventoryPage = new InventoryPage(driver);
    }
}

// Batch database operations
saveMultipleUsers(userList); // Single query instead of loop
```

### ❌ DON'T
```java
// Don't create unnecessary browser instances
for (int i = 0; i < 5; i++) {
    WebDriver driver = new ChromeDriver();
    // ❌ Creates 5 browser instances
}

// Don't navigate back and forth unnecessarily
driver.navigate().back();
driver.navigate().forward();
driver.navigate().back();

// Don't reload page repeatedly
driver.navigate().refresh(); // Only if necessary
```

---

## 13. Security Best Practices

### ✅ DO
```java
// Use environment variables for sensitive data
String password = System.getenv("TEST_PASSWORD");

// Mask sensitive data in logs
LoggerUtil.info("Login with user: " + username + " (password hidden)");

// Use HTTPS connections
String url = "https://secure.application.com";

// Clear sensitive data after use
char[] password = // Use char[] instead of String
Arrays.fill(password, '0');
```

### ❌ DON'T
```java
// Don't hardcode credentials
String password = "MySecurePassword123";

// Don't log passwords or tokens
LoggerUtil.info("Authorization: Bearer " + token);

// Don't store credentials in version control
// config.properties with actual passwords

// Don't use HTTP for sensitive operations
String url = "http://insecure.application.com";
```

---

## 14. Reporting and Documentation

### ✅ DO
```java
// Generate meaningful reports
// Include test name, duration, status
// Attach screenshots for failures
// Include system information

// Document test scenarios clearly
/**
 * Test: User successful login
 * Precondition: User on login page
 * Steps: Enter credentials and login
 * Expected: Should see dashboard
 */

// Keep test documentation updated
Feature files with clear scenarios
Page Object documentation
Utility class documentation
```

### ❌ DON'T
```java
// Don't generate useless reports
// Complex HTML with no useful info
// No failures captured
// No performance metrics

// Don't leave code undocumented
public void method1() { }
public void test() { }

// Don't have outdated documentation
// Mismatch between code and docs
// Old feature file descriptions
```

---

## 15. Continuous Improvement

### ✅ DO
```java
// Monitor test failures and flakiness
// Identify and fix flaky tests
// Review execution logs regularly
// Update tests as application evolves

// Refactor when needed
// Remove code duplication
// Improve readability
// Optimize performance

// Share knowledge
// Document complex scenarios
// Code reviews
// Knowledge sharing sessions
```

### ❌ DON'T
```java
// Don't ignore test failures
// Keep failing tests in the suite
// Mark tests as ignored without reason

// Don't leave technical debt
// Duplicate code
// Unmaintainable test logic
// Outdated selenium APIs

// Don't skip updates
// Use outdated library versions
// Ignore security warnings
// Skip framework updates
```

---

## Summary Checklist

- [ ] Use Page Object Model for all pages
- [ ] Implement comprehensive logging
- [ ] Handle exceptions properly
- [ ] Use explicit waits (not Thread.sleep)
- [ ] Keep test data external
- [ ] Use ConfigReader for configuration
- [ ] Implement proper assertions with messages
- [ ] Use ThreadLocal for parallel execution
- [ ] Document complex logic
- [ ] Follow naming conventions
- [ ] Review code before commit
- [ ] Keep tests DRY (Don't Repeat Yourself)
- [ ] Maintain separation of concerns
- [ ] Use meaningful error messages
- [ ] Regular refactoring and cleanup

---

**Follow these practices to maintain a professional, maintainable automation framework!**

Last Updated: June 2, 2026

