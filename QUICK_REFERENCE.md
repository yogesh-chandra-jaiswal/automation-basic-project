# ⚡ Quick Reference Guide

## 🎯 Framework at a Glance

**Framework:** Selenium Cucumber BDD (Behavior Driven Development)  
**Java Version:** 11+  
**Status:** ✅ Production Ready  
**Last Updated:** June 2, 2026  

---

## ⚡ Quick Commands

```bash
# Setup & Install
mvn clean install                    # Install all dependencies
mvn clean install -U                 # Force update dependencies

# Basic Test Execution
mvn clean test                       # Run all tests (Chrome)
mvn clean test -Dbrowser=chrome      # Explicit Chrome
mvn clean test -Dbrowser=edge        # Run on Edge
mvn clean test -Dbrowser=firefox     # Run on Firefox

# Parallel Execution
mvn clean test -DthreadCount=3       # 3 parallel threads
mvn clean test -DthreadCount=5       # 5 parallel threads

# Specific Tests
mvn clean test -Dcucumber.options="src/main/resources/featuresFiles/Login.feature"
mvn clean test -Dcucumber.options="--dry-run"      # Validate steps
mvn clean test -Dcucumber.options="--tags @smoke"  # Run tagged tests

# Configuration
mvn clean test -Denvironment=staging # Staging environment
mvn clean test -Denvironment=prod    # Production environment
```

---

## 📁 Key File Locations

| Item | Location | Purpose |
|------|----------|---------|
| Config | `src/main/resources/config.properties` | All settings |
| Logs | `target/logs/` | Generated logs |
| Reports | `target/cucumber-reports/` | Test reports |
| Screenshots | `target/screenshots/` | Failure screenshots |
| Features | `src/main/resources/featuresFiles/` | Test scenarios |

---

## 🔧 Core Classes Quick Reference

### Page Objects
```java
LoginPage           → Login page automation
InventoryPage       ��� Inventory page automation
// Extend BasePage for new pages
```

### Utilities
```java
LoggerUtil          → Logging: LoggerUtil.info("message")
JavascriptUtil      → JS: jsUtil.scrollToElement(element)
WaitUtil            → Waits: waitUtil.waitForElementToBeClickable(locator, 15)
ScreenshotUtil      → Screenshots: screenshotUtil.takeScreenshot("name")
FileUtil            → Files: FileUtil.readFileAsString("path")
AlertUtil           → Alerts: alertUtil.acceptAlert()
SelectUtil          → Dropdowns: selectUtil.selectByVisibleText(locator, "text")
WindowHandleUtil    → Windows: windowHandleUtil.switchToNewWindow()
JsonUtil            → JSON: JsonUtil.objectToJson(object)
```

### Base Class
```java
BasePage
├── click(locator)                   // Click element
├── sendKeys(locator, text)          // Send text
├── getText(locator)                 // Get element text
├── isElementPresent(locator)        // Check visibility
├── waitForPageLoad()                // Wait for page
└── takeScreenshot(fileName)         // Capture screenshot
```

---

## 📊 Configuration Quick Reference

### Common Properties
```properties
# Browser
browser=chrome                       # Browser type
headless=false                       # Run headless

# Timeouts (seconds)
implicit.wait=10                     # For findElement
explicit.wait=15                     # For wait conditions
page.load.timeout=20                 # Page load

# Paths
screenshot.path=target/screenshots/
report.path=target/cucumber-reports/
log.file.path=target/logs/

# Credentials
username=standard_user
password=secret_sauce

# Logging
log.level=INFO
```

---

## 🧪 Writing Tests

### 1. Create Feature File
```gherkin
# src/main/resources/featuresFiles/MyFeature.feature
Feature: User Actions

  Scenario: Perform action
    Given User is on page
    When User performs action
    Then User sees result
```

### 2. Create Page Object
```java
// src/main/java/pages/MyPage.java
public class MyPage extends BasePage {
    private By element = By.id("element");
    
    public MyPage(WebDriver driver) {
        super(driver);
    }
    
    public void clickElement() {
        click(element);
    }
}
```

### 3. Create Step Definition
```java
// src/main/java/steps/MySteps.java
public class MySteps {
    private WebDriver driver;
    private MyPage myPage;
    
    private void initPages() {
        driver = Hooks.getDriver();
        myPage = new MyPage(driver);
    }
    
    @Given("User is on page")
    public void user_on_page() {
        initPages();
        myPage.navigateTo();
    }
}
```

---

## 🚀 Execution Flow

```
1. mvn clean test
   ↓
2. Hooks.clearPreviousReports()     (Clear old reports)
   ↓
3. Hooks.setUp()                     (Launch browser)
   ↓
4. Feature File Execution            (Run scenarios)
   ├── @Given step
   ├── @When step
   └── @Then step
   ↓
5. Hooks.takeScreenshotOnFailure()   (If failed)
   ↓
6. Hooks.tearDown()                  (Close browser)
   ↓
7. Reports Generated                 (HTML, JSON, XML)
```

---

## 📊 Reports Location

```
Open in Browser:
file:///C:/Users/DEEPA VERMA/IdeaProjects/automation-bdd/target/cucumber-reports/cucumber.html
```

---

## 🐛 Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| WebDriver not found | Run `mvn clean install` |
| Element not found | Increase `explicit.wait` in config.properties |
| Timeout errors | Check element locator, verify application loaded |
| Build failure | Run `mvn clean install -U` to update deps |
| Permission denied | Run as administrator or check folder permissions |

---

## 📝 Logging Output

```
INFO  : [Thread-1] ===== Starting Test Scenario =====
INFO  : [Thread-1] Launching chrome browser...
INFO  : [Thread-1] Navigating to: https://www.saucedemo.com/
DEBUG : [Thread-1] Element found: By.id(user-name)
INFO  : [Thread-1] Test PASSED - Scenario Name
INFO  : [Thread-1] ===== Test Scenario Completed =====
```

---

## ✅ Checklist Before Running Tests

- [ ] Java 11+ installed: `java -version`
- [ ] Maven installed: `mvn -version`
- [ ] Dependencies installed: `mvn clean install`
- [ ] config.properties updated
- [ ] Feature files created
- [ ] Step definitions implemented
- [ ] Page objects created
- [ ] WebDriver manager downloaded

---

## 🔑 Key Concepts

### ThreadLocal
```java
// Thread-safe WebDriver management
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
```

### BasePage Pattern
```java
// All pages inherit from BasePage
public class LoginPage extends BasePage {
    // Inherits all common methods
}
```

### ConfigReader
```java
// Centralized configuration
String url = ConfigReader.getProperty("app.url");
int timeout = ConfigReader.getIntProperty("explicit.wait");
```

### Utilities
```java
// Pre-built utility classes ready to use
LoggerUtil.info("Message");
jsUtil.scrollToElement(element);
waitUtil.waitForElementToBeClickable(locator, 15);
```

---

## 📚 Documentation Quick Links

| Document | Purpose |
|----------|---------|
| README.md | Complete framework overview |
| CONFIGURATION.md | Configuration details |
| BEST_PRACTICES.md | Development guidelines |
| EXECUTION_GUIDE.md | How to run tests |
| PROJECT_OVERVIEW.md | Project summary |

---

## 💡 Tips & Tricks

```bash
# Clean all generated files
mvn clean

# Skip tests during build
mvn clean install -DskipTests

# Run tests with debug logging
mvn clean test -X

# View Surefire reports
mvn surefire-report:report

# Generate site documentation
mvn site

# Check for dependency updates
mvn versions:display-dependency-updates
```

---

## 🎯 Common Use Cases

### Run Login Tests on Chrome
```bash
mvn clean test -Dbrowser=chrome -Dcucumber.options="Login.feature"
```

### Run All Tests in Parallel
```bash
mvn clean test -DthreadCount=5
```

### Test Staging Environment
```bash
mvn clean test -Denvironment=staging
```

### Validate Test Steps (No Execution)
```bash
mvn clean test -Dcucumber.options="--dry-run"
```

### Run Only Smoke Tests
```bash
mvn clean test -Dcucumber.options="--tags @smoke"
```

---

## 📞 Getting Help

1. **Check Logs** → `target/logs/automation.log`
2. **View Reports** → `target/cucumber-reports/cucumber.html`
3. **Review Screenshots** → `target/screenshots/`
4. **Read Docs** → README.md, BEST_PRACTICES.md
5. **Check Code Comments** → Java files have inline docs

---

## ✨ Framework Features at a Glance

✅ Page Object Model  
✅ BDD with Cucumber  
✅ Cross-browser testing  
✅ Parallel execution  
✅ Advanced logging  
✅ Screenshot on failure  
✅ Centralized configuration  
✅ 9 utility classes  
✅ Complete documentation  
✅ Production-ready  

---

## 🎓 Quick Learning Path

1. Read **README.md** (5 min)
2. Run **`mvn clean install`** (2 min)
3. Check **config.properties** (2 min)
4. Run **`mvn clean test`** (5 min)
5. View **target/cucumber-reports/cucumber.html** (2 min)
6. Review **BEST_PRACTICES.md** (10 min)
7. Explore **page objects** (10 min)
8. Create **your first test** (15 min)

**Total: ~45 minutes to get productive!**

---

## 🚀 Ready to Start?

```bash
# Step 1
mvn clean install

# Step 2
mvn clean test

# Step 3
# Open: target/cucumber-reports/cucumber.html
```

**That's it! Your tests are running! 🎉**

---

**Quick Reference Version 1.0**  
**Last Updated: June 2, 2026**

