# Framework Configuration Guide

## Overview
This document provides comprehensive guidance on configuring and using the Automation BDD Framework.

---

## 1. Browser Configuration

### Supported Browsers
- **Chrome** (Default)
- **Edge**
- **Firefox**

### Set Browser at Runtime
```bash
mvn clean test -Dbrowser=edge
mvn clean test -Dbrowser=firefox
```

### Browser Options
```properties
browser=chrome
headless=false
disable.notifications=true
start.maximized=true
```

---

## 2. Timeout Configuration

### Implicit Wait
Applied to all WebDriver.findElement() calls
```properties
implicit.wait=10  # seconds
```

### Explicit Wait
Used in WaitUtil for specific conditions
```properties
explicit.wait=15  # seconds
```

### Page Load Timeout
Maximum time to wait for page load
```properties
page.load.timeout=20  # seconds
```

---

## 3. Credentials Management

### Test Credentials
```properties
username=standard_user
password=secret_sauce
locked_user=locked_out_user
problem_user=problem_user
```

### Best Practices
- Never hardcode credentials in test files
- Use ConfigReader to fetch credentials
- Store sensitive credentials in environment variables
- Implement credential encryption for production

---

## 4. Logging Configuration

### Log Levels
```
DEBUG  - Detailed information for debugging
INFO   - General information messages (default)
WARN   - Warning messages
ERROR  - Error messages
FATAL  - Fatal error messages
```

### Log Output Files
- `automation.log` - All logs
- `error.log` - Errors only
- `info.log` - Info logs only

### Configure Logging Level
Update `src/main/resources/log4j2.xml`:
```xml
<Root level="INFO">  <!-- Change to DEBUG for verbose logging -->
```

---

## 5. Parallel Execution

### Configuration
```properties
parallel.execution=true
parallel.threads=3
```

### Running in Parallel
```bash
mvn clean test -Dparallel=true -DthreadCount=3
```

### Thread Safety
All components use ThreadLocal for thread-safe execution:
```java
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
```

---

## 6. Screenshot Configuration

### Enable/Disable Screenshots
```properties
screenshot.on.failure=true
```

### Screenshot Path
```properties
screenshot.path=target/screenshots/
```

### Screenshot Naming Convention
Format: `ScenarioName_BrowserName_FAILED_timestamp.png`

Example: `Login_Test_chrome_FAILED_2026-06-02_14-35-22-123.png`

---

## 7. Reporting Configuration

### Report Paths
```properties
report.path=target/cucumber-reports/
html.report.path=target/cucumber-html-reports/
```

### Report Types Generated
- **Cucumber HTML** - Human-readable HTML report
- **Cucumber JSON** - Machine-readable JSON format
- **JUnit XML** - Integration with CI/CD tools

---

## 8. Environment Configuration

### Available Environments
```properties
environment=staging  # dev, staging, production
```

### Switch Environments
```bash
mvn clean test -Denvironment=production
```

---

## 9. Retry Configuration

### Enable Test Retry
```properties
retry.enabled=false
retry.count=2
```

---

## 10. Database Configuration (Optional)

### Configure Database
```properties
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/automation_db
db.username=root
db.password=password
```

---

## 11. API Configuration (Optional)

### Configure API Endpoints
```properties
api.base.url=https://api.example.com
api.timeout=5000
api.retry.count=3
```

---

## 12. Proxy Configuration (Optional)

### Enable Proxy
```properties
proxy.enabled=false
proxy.host=proxy.company.com
proxy.port=8080
```

---

## 13. Advanced Configuration

### Headless Mode
```properties
headless=true  # Run browser without GUI
```

### Disable Notifications
```properties
disable.notifications=true
```

### Start Maximized
```properties
start.maximized=true
```

---

## 14. ConfigReader Usage

### Reading Configuration Values

#### String Values
```java
String value = ConfigReader.getProperty("key");
String valueWithDefault = ConfigReader.getProperty("key", "defaultValue");
```

#### Integer Values
```java
int value = ConfigReader.getIntProperty("implicit.wait");
```

#### Boolean Values
```java
boolean value = ConfigReader.getBooleanProperty("screenshot.on.failure");
```

---

## 15. Environment-Specific Configuration

### Create Environment-Specific Property Files

**For Development:**
```
config-dev.properties
```

**For Staging:**
```
config-staging.properties
```

**For Production:**
```
config-prod.properties
```

### Load Based on Environment
```java
String environment = System.getProperty("environment", "staging");
String configFile = "config-" + environment + ".properties";
```

---

## 16. CI/CD Integration

### Jenkins Example
```bash
mvn clean test \
  -Dbrowser=chrome \
  -Dparallel=true \
  -DthreadCount=3 \
  -Denvironment=staging
```

### GitHub Actions Example
```yaml
- name: Run Tests
  run: mvn clean test -Dbrowser=chrome -Dparallel=true -DthreadCount=3
```

---

## 17. Performance Tuning

### Recommended Settings for Performance
```properties
# Reduce waits for faster execution
implicit.wait=5
explicit.wait=10
page.load.timeout=15

# Enable parallel execution
parallel.threads=5

# Disable screenshots for speed
screenshot.on.failure=false
```

### Recommended Settings for Reliability
```properties
# Increase waits for slower applications
implicit.wait=15
explicit.wait=20
page.load.timeout=30

# Disable parallel if tests interfere
parallel.threads=1

# Enable screenshots for debugging
screenshot.on.failure=true
```

---

## 18. Security Considerations

### Never Commit
- Sensitive credentials
- API keys
- Database passwords

### Secure Alternatives
```bash
# Use environment variables
export TEST_USERNAME=username
export TEST_PASSWORD=password

# Read in code
String username = System.getenv("TEST_USERNAME");
```

---

## 19. Troubleshooting Configuration Issues

### Issue: Configuration Not Found
**Solution:** Ensure `config.properties` is in `src/main/resources/`

### Issue: Value Returns Null
**Solution:** Check property key spelling and ensure it exists in `config.properties`

### Issue: Type Conversion Error
**Solution:** Ensure using correct method: `getProperty()`, `getIntProperty()`, `getBooleanProperty()`

---

## 20. Configuration Validation

### Validate Configuration
```java
@Before
public void validateConfig() {
    String appUrl = ConfigReader.getProperty("app.url");
    if (appUrl == null || appUrl.isEmpty()) {
        throw new IllegalArgumentException("app.url is not configured");
    }
}
```

---

**Last Updated:** June 2, 2026

