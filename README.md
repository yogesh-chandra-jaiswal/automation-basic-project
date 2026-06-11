# Automation BDD Framework

## 📋 Overview
This is a **comprehensive, enterprise-grade Selenium Cucumber BDD (Behavior Driven Development)** automation framework designed for robust web application testing with support for cross-browser testing and parallel execution.

---

## ✨ Key Features

✅ **Behavior Driven Development** - Cucumber-based framework with Gherkin syntax  
✅ **Page Object Model** - Scalable and maintainable page objects  
✅ **Cross-Browser Testing** - Support for Chrome, Edge, and Firefox  
✅ **Parallel Execution** - Thread-safe execution for faster test runs  
✅ **Comprehensive Logging** - Log4j2 with multiple appenders  
✅ **Advanced Reporting** - Cucumber HTML and JSON reports  
✅ **Utility Classes** - JavaScript execution, file operations, database, API, etc.  
✅ **ConfigReader** - Centralized configuration management  
✅ **Screenshot on Failure** - Automatic screenshot capture  
✅ **WebDriverManager** - Automatic driver management  
✅ **Thread-Safe** - ThreadLocal for thread safety in parallel execution  

---

## 🛠️ Technologies Used

| Component | Version | Purpose |
|-----------|---------|---------|
| Java | 11 | Programming Language |
| Selenium WebDriver | 4.15.0 | Web Automation |
| Cucumber | 7.14.0 | BDD Framework |
| TestNG | 7.9.0 | Test Execution |
| JUnit | 4.13.2 | Test Framework |
| Log4j2 | 2.23.1 | Logging |
| WebDriverManager | 5.9.0 | Driver Management |
| Gson | 2.10.1 | JSON Processing |
| Maven | Latest | Build Tool |

---

## 📁 Project Structure

```
automation-bdd/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── common/
│   │   │   │   └── BasePage.java              # Base class for all pages
│   │   │   ├── config/
│   │   │   │   └── ConfigReader.java          # Configuration reader
│   │   │   ├── hooks/
│   │   │   │   └── Hooks.java                 # Test lifecycle hooks
│   │   │   ├── pages/
│   │   │   │   ├── LoginPage.java             # Login page object
│   │   │   │   ├── InventoryPage.java         # Inventory page object
│   │   │   │   └── ...
│   │   │   ├── steps/
│   │   │   │   ├── LoginSteps.java            # Login step definitions
│   │   │   │   └── ...
│   │   │   └── utils/
│   │   │       ├── LoggerUtil.java            # Logging utility
│   │   │       ├── JavascriptUtil.java        # JavaScript execution
│   │   │       ├── WaitUtil.java              # Wait utilities
│   │   │       ├── ScreenshotUtil.java        # Screenshot utility
│   │   │       ├── FileUtil.java              # File operations
│   │   │       ├── AlertUtil.java             # Alert handling
│   │   │       ├── SelectUtil.java            # Dropdown operations
│   │   │       ├── WindowHandleUtil.java      # Window handling
│   │   │       ├── JsonUtil.java              # JSON operations
│   │   │       └── ...
│   │   └── resources/
│   │       ├── config.properties              # Configuration file
│   │       ├── log4j2.xml                     # Log4j2 configuration
│   │       └── featuresFiles/
│   │           └── Login.feature              # Gherkin feature files
│   │
│   └── test/
│       ├── java/
│       │   └── TestRunner.java                # Cucumber test runner
│       └── resources/
│           └── testng.xml                     # TestNG configuration
│
├── target/
│   ├── screenshots/                           # Failed test screenshots
│   ├── cucumber-reports/                      # Test reports
│   ├── logs/                                  # Application logs
│   └── ...
│
└── pom.xml                                    # Maven configuration
```

---

## 🚀 Getting Started

### Prerequisites
- **Java 11+** installed
- **Maven 3.6+** installed
- **Git** installed

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd automation-bdd
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Verify installation**
   ```bash
   mvn -v
   java -version
   ```

---

## 🎯 Running Tests

### Run All Tests (Chrome Browser)
```bash
mvn clean test
```

### Run with Specific Browser
```bash
# Chrome (Default)
mvn clean test -Dbrowser=chrome

# Edge
mvn clean test -Dbrowser=edge

# Firefox
mvn clean test -Dbrowser=firefox
```

### Run Specific Feature File
```bash
mvn clean test -Dcucumber.options="src/main/resources/featuresFiles/Login.feature"
```

### Run in Parallel Mode
```bash
mvn clean test -Dparallel=true -DthreadCount=3
```

### Run Specific Scenario
```bash
mvn clean test -Dcucumber.options="src/main/resources/featuresFiles/Login.feature:5"
```

### Dry Run (Validate Steps without Execution)
```bash
mvn clean test -Dcucumber.options="--dry-run"
```

---

## 📊 Test Reports

### Cucumber HTML Report
Located at: `target/cucumber-reports/cucumber.html`

### JSON Report
Located at: `target/cucumber-reports/cucumber.json`

### JUnit XML Report
Located at: `target/cucumber-reports/cucumber.xml`

### Application Logs
- **All Logs**: `target/logs/automation.log`
- **Error Logs**: `target/logs/error.log`
- **Info Logs**: `target/logs/info.log`

### Screenshots
Located at: `target/screenshots/` (captured only on test failure)

---

## 📝 Writing Tests

### 1. Create Feature File (Gherkin)
```gherkin
# src/main/resources/featuresFiles/Login.feature
Feature: User Login

  Scenario: Successful login with valid credentials
    Given I am on the Sauce Demo login page
    When I enter valid username and password
    And I click the login button
    Then I should be redirected to the inventory page
```

### 2. Create Page Object
```java
// src/main/java/pages/MyPage.java
public class MyPage extends BasePage {
    private By element = By.id("element-id");
    
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
    
    private void initializePageObjects() {
        driver = Hooks.getDriver();
        myPage = new MyPage(driver);
    }
    
    @Given("Step definition")
    public void stepDefinition() {
        initializePageObjects();
        myPage.clickElement();
    }
}
```

---

## 🔧 Configuration

### config.properties
All configuration values are centralized in `src/main/resources/config.properties`:

```properties
# Browser Settings
browser=chrome
headless=false

# Timeouts
implicit.wait=10
explicit.wait=15
page.load.timeout=20

# Credentials
username=standard_user
password=secret_sauce

# Paths
screenshot.path=target/screenshots/
report.path=target/cucumber-reports/
log.file.path=target/logs/

# Logging
log.level=INFO
```

### Update Configuration at Runtime
```bash
mvn clean test -Dbrowser=edge -Dimplicit.wait=20
```

---

## 🛠️ Utility Classes

### LoggerUtil
```java
LoggerUtil.info("Information message");
LoggerUtil.debug("Debug message");
LoggerUtil.error("Error message", exception);
LoggerUtil.warn("Warning message");
```

### JavascriptUtil
```java
jsUtil.clickElementByJS(element);
jsUtil.scrollToElement(element);
jsUtil.scrollToBottom();
jsUtil.getAttribute(element, "attribute");
```

### WaitUtil
```java
waitUtil.waitForElementToBeVisible(locator, 15);
waitUtil.waitForElementToBeClickable(locator, 10);
waitUtil.waitForPageTitle("Title", 20);
```

### ScreenshotUtil
```java
screenshotUtil.takeScreenshot("test-name");
screenshotUtil.takeScreenshotOnFailure("failed-test");
```

### FileUtil
```java
FileUtil.createDirectory("path/to/dir");
FileUtil.deleteFile("path/to/file");
FileUtil.copyFile("source", "destination");
FileUtil.readFileAsString("path/to/file");
```

### AlertUtil
```java
alertUtil.acceptAlert();
alertUtil.dismissAlert();
String text = alertUtil.getAlertText();
alertUtil.sendTextToAlert("text");
```

### SelectUtil
```java
selectUtil.selectByVisibleText(locator, "Option");
selectUtil.selectByValue(locator, "value");
selectUtil.selectByIndex(locator, 0);
int count = selectUtil.getOptionCount(locator);
```

---

## ⚙️ Advanced Features

### Cross-Browser Testing
```bash
# Execute tests on multiple browsers sequentially
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=edge
mvn clean test -Dbrowser=firefox
```

### Parallel Execution
```bash
# Run 3 tests in parallel
mvn clean test -Dparallel=true -DthreadCount=3
```

### Screenshot Capture
- Automatically captures screenshots on test failure
- Located in `target/screenshots/`
- Filename format: `ScenarioName_BrowserName_FAILED_timestamp.png`

### Comprehensive Logging
- Log4j2 configuration in `src/main/resources/log4j2.xml`
- Multiple log files with different levels
- Console and file output

---

## 🧪 Test Execution Workflow

1. **Hooks.clearPreviousReports()** - Cleans old reports
2. **Hooks.setUp()** - Initializes WebDriver
3. **Step Definitions** - Execute BDD scenarios
4. **Assertions** - Validate expected results
5. **Hooks.takeScreenshotOnFailure()** - Captures failure screenshots
6. **Hooks.tearDown()** - Closes browser and cleans up

---

## 🔒 Best Practices

✅ Always use **Page Object Model** for page interactions  
✅ Use **ConfigReader** for all configuration values  
✅ Utilize **Hooks** for test setup and teardown  
✅ Implement comprehensive **logging**  
✅ Use **explicit waits** instead of implicit waits  
✅ Capture **screenshots on failure**  
✅ Write **readable Gherkin scenarios**  
✅ Keep **step definitions simple and focused**  
✅ Use **ThreadLocal** for parallel execution  
✅ Follow **DRY principle** in test code  

---

## 📚 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [TestNG Documentation](https://testng.org/doc/)
- [Log4j2 Documentation](https://logging.apache.org/log4j/2.x/)
- [Maven Documentation](https://maven.apache.org/guides/)

---

## 🐛 Troubleshooting

### Issue: WebDriver Not Found
```bash
Solution: Run 'mvn clean install' to download WebDriver
```

### Issue: Element Not Found
```bash
Solution: Increase explicit.wait in config.properties
```

### Issue: Port Already in Use
```bash
Solution: Kill process using the port or use different port
```

### Issue: Maven Build Failure
```bash
Solution: Run 'mvn clean install -U' to force update dependencies
```

---

## 📞 Support

For issues or questions:
1. Check the logs in `target/logs/`
2. Review the screenshots in `target/screenshots/`
3. Check the test reports in `target/cucumber-reports/`

---

## 📄 License

This project is licensed under the MIT License.

---

## ✅ Checklist for Production Use

- [ ] Update `config.properties` with actual URLs and credentials
- [ ] Configure database connection (if needed)
- [ ] Set up CI/CD pipeline
- [ ] Configure reporting dashboard
- [ ] Add custom utility classes as needed
- [ ] Implement retry logic for flaky tests
- [ ] Set up test data management
- [ ] Configure logging levels for production
- [ ] Add security scanning for credentials
- [ ] Document test scenarios

---

**Happy Testing! 🎉**

