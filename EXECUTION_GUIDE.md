# Testing and Execution Guide

## 🚀 Quick Start

### 1. First Time Setup
```bash
# Clone repository (if applicable)
cd automation-bdd

# Install all dependencies
mvn clean install -U

# Verify Maven installation
mvn -v

# Verify Java installation
java -version
```

---

## 🎯 Test Execution Scenarios

### Scenario 1: Basic Test Execution
```bash
# Run all tests with default configuration (Chrome browser)
mvn clean test

# Expected output:
# [INFO] Tests run: X, Failures: 0, Skipped: 0
# [INFO] BUILD SUCCESS
```

### Scenario 2: Cross-Browser Testing
```bash
# Test with Chrome
mvn clean test -Dbrowser=chrome

# Test with Edge
mvn clean test -Dbrowser=edge

# Test with Firefox
mvn clean test -Dbrowser=firefox
```

### Scenario 3: Parallel Execution
```bash
# Run 3 tests in parallel
mvn clean test -DthreadCount=3

# Run 5 tests in parallel
mvn clean test -DthreadCount=5

# Run specific number of threads
mvn clean test -Dparallel=true -DthreadCount=3
```

### Scenario 4: Run Specific Feature
```bash
# Run only Login feature
mvn clean test -Dcucumber.options="src/main/resources/featuresFiles/Login.feature"

# Run specific scenario by line number
mvn clean test -Dcucumber.options="src/main/resources/featuresFiles/Login.feature:5"

# Run with tags
mvn clean test -Dcucumber.options="--tags @smoke"
```

### Scenario 5: Dry Run (Validate Steps)
```bash
# Check if all steps have implementation
mvn clean test -Dcucumber.options="--dry-run"

# Expected output shows all step matches without execution
```

### Scenario 6: Environment-Specific Testing
```bash
# Run on Development environment
mvn clean test -Denvironment=dev

# Run on Staging environment
mvn clean test -Denvironment=staging

# Run on Production environment
mvn clean test -Denvironment=production
```

### Scenario 7: Combined Execution
```bash
# Chrome browser, 3 parallel threads, staging environment
mvn clean test \
  -Dbrowser=chrome \
  -DthreadCount=3 \
  -Denvironment=staging

# All configurations from CLI
mvn clean test \
  -Dbrowser=edge \
  -Dparallel=true \
  -DthreadCount=5 \
  -Denvironment=production \
  -Dimplicit.wait=15
```

---

## 📊 Output and Reports

### Where to Find Reports

#### 1. Cucumber HTML Report
```
Location: target/cucumber-reports/cucumber.html
Description: Human-readable HTML report
How to open: Right-click → Open in Browser
```

#### 2. JSON Report
```
Location: target/cucumber-reports/cucumber.json
Description: Machine-readable format for CI/CD integration
Usage: Used by reporting plugins and CI/CD tools
```

#### 3. JUnit XML Report
```
Location: target/cucumber-reports/cucumber.xml
Description: XML format for test integration
Integration: Jenkins, GitHub Actions, Azure DevOps
```

#### 4. Application Logs
```
Location: target/logs/
Files:
  - automation.log       (All logs)
  - error.log           (Errors only)
  - info.log            (Info logs only)
```

#### 5. Screenshots
```
Location: target/screenshots/
Naming: ScenarioName_BrowserName_FAILED_timestamp.png
Captured: Only on test failure
```

---

## 🔍 Test Execution Examples

### Example 1: Simple Login Test
```bash
$ mvn clean test

# Output:
# [INFO] -------------------------------------------------------
# [INFO] T E S T S
# [INFO] -------------------------------------------------------
# [INFO] Running TestRunner
# [INFO] ===== Starting Test Scenario =====
# [INFO] Launching chrome browser...
# [INFO] Navigating to: https://www.saucedemo.com/
# [INFO] Successfully navigated to login page
# [INFO] Performing login with username: standard_user
# [INFO] Successfully entered credentials
# [INFO] Successfully clicked login button
# [INFO] Successfully verified inventory page
# [INFO] Test PASSED - I should be redirected to the inventory page
# [INFO] ===== Test Scenario Completed =====
# [INFO] Tests run: 1, Failures: 0, Skipped: 0
# [INFO] BUILD SUCCESS
```

### Example 2: Failed Test
```bash
$ mvn clean test

# Output:
# [ERROR] Test FAILED - Screenshot: Scenario_chrome_FAILED_2026-06-02_14-35-22.png
# [ERROR] Failed to verify inventory page
# [INFO] Tests run: 1, Failures: 1, Skipped: 0
# [ERROR] BUILD FAILURE
```

### Example 3: Parallel Execution (3 browsers)
```bash
$ mvn clean test -Dbrowser=chrome -DthreadCount=3

# Output (simplified):
# [INFO] Thread-1: Launching chrome browser...
# [INFO] Thread-2: Launching chrome browser...
# [INFO] Thread-3: Launching chrome browser...
# [INFO] Thread-1: Test PASSED
# [INFO] Thread-2: Test PASSED
# [INFO] Thread-3: Test PASSED
# [INFO] Tests run: 3, Failures: 0, Skipped: 0
# [INFO] BUILD SUCCESS
```

---

## 🛠️ Troubleshooting Execution

### Issue 1: Maven Command Not Found
```bash
Error: 'mvn' is not recognized as an internal or external command

Solution:
1. Install Maven from https://maven.apache.org/download.cgi
2. Add Maven bin directory to PATH environment variable
3. Restart terminal/IDE
4. Run: mvn -v to verify
```

### Issue 2: Java Compilation Error
```bash
Error: [ERROR] COMPILATION ERROR: ... cannot find symbol

Solution:
1. Ensure Java 11+ is installed: java -version
2. Run: mvn clean install -U
3. Clear IDE cache and rebuild project
4. Check that all import statements are correct
```

### Issue 3: WebDriver Not Found
```bash
Error: 'chromedriver' not found

Solution:
1. WebDriverManager should handle this automatically
2. If still fails: mvn clean install
3. Ensure internet connection (for driver download)
4. Check proxy settings in config.properties
```

### Issue 4: Tests Not Running
```bash
Error: No tests found to run

Solution:
1. Verify TestRunner.java exists in src/test/java
2. Ensure feature files in src/main/resources/featuresFiles/
3. Check glue path in @CucumberOptions
4. Verify step implementations exist
5. Run dry-run first: mvn clean test -Dcucumber.options="--dry-run"
```

### Issue 5: Timeout Errors
```bash
Error: TimeoutException: Timeout waiting for element

Solution:
1. Increase explicit.wait in config.properties
2. Verify element locator is correct
3. Check if application is slow
4. Run with: mvn clean test -Dexplicit.wait=30
```

### Issue 6: Permission Denied (Screenshots)
```bash
Error: Permission denied - target/screenshots/

Solution:
1. Ensure target/ directory exists and writable
2. Run: mvn clean (to recreate directories)
3. Check file permissions in target folder
4. Try running as administrator
```

---

## 📈 Performance Optimization

### For Faster Execution
```bash
# Reduce timeouts for faster execution
mvn clean test \
  -Dimplicit.wait=5 \
  -Dexplicit.wait=10 \
  -Dpage.load.timeout=15

# Run in parallel with more threads
mvn clean test -DthreadCount=8

# Disable screenshot capture
mvn clean test -Dscreenshot.on.failure=false

# Skip report generation during test
mvn clean test -DskipReports=true
```

### For More Reliable Execution
```bash
# Increase timeouts
mvn clean test \
  -Dimplicit.wait=20 \
  -Dexplicit.wait=30 \
  -Dpage.load.timeout=40

# Run sequentially (no parallel)
mvn clean test -DthreadCount=1

# Enable all logging
mvn clean test -Dlog.level=DEBUG

# Capture all screenshots
mvn clean test -Dscreenshot.on.failure=true
```

---

## 🔄 Continuous Integration Setup

### GitHub Actions Example
```yaml
name: Run Automation Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up Java
      uses: actions/setup-java@v2
      with:
        java-version: '11'
    
    - name: Run Tests
      run: mvn clean test -Dbrowser=chrome -DthreadCount=3
    
    - name: Upload Reports
      if: always()
      uses: actions/upload-artifact@v2
      with:
        name: cucumber-reports
        path: target/cucumber-reports/
```

### Jenkins Pipeline Example
```groovy
pipeline {
    agent any
    
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test -Dbrowser=chrome -DthreadCount=3'
            }
        }
        
        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'target/cucumber-reports',
                    reportFiles: 'cucumber.html',
                    reportName: 'Cucumber Report'
                ])
            }
        }
    }
}
```

---

## 📋 Test Execution Checklist

### Before Running Tests
- [ ] Update `config.properties` with correct URLs
- [ ] Verify test credentials are valid
- [ ] Check internet connection
- [ ] Ensure all dependencies are installed: `mvn clean install`
- [ ] Verify Java and Maven versions
- [ ] Close unnecessary applications (for resource availability)

### During Test Execution
- [ ] Monitor console output for errors
- [ ] Check for timeouts and failures
- [ ] Verify browser launches correctly
- [ ] Monitor system resources
- [ ] Do not interact with browser (if headless=false)

### After Test Execution
- [ ] Review test results in target/cucumber-reports/
- [ ] Check logs in target/logs/
- [ ] Review screenshots for failed tests
- [ ] Analyze failure reasons
- [ ] Document any issues found
- [ ] Cleanup old reports if needed

---

## 🎓 Understanding Test Output

### Successful Test Output
```
[INFO] Running TestRunner
[INFO] ===== Starting Test Scenario =====
[INFO] Launching chrome browser...
[INFO] Test PASSED - Scenario Name
[INFO] ===== Test Scenario Completed =====
[INFO] BUILD SUCCESS
```

### Failed Test Output
```
[ERROR] Test FAILED - Screenshot: scenario_FAILED_timestamp.png
[ERROR] AssertionError: Expected 'Dashboard' but found 'Error'
[INFO] BUILD FAILURE
```

### Timeout Output
```
[ERROR] TimeoutException: Timeout waiting for element: By.id(element)
[ERROR] Unable to find element within 15 seconds
[INFO] BUILD FAILURE
```

---

## 💡 Pro Tips

1. **Use Tags for Organized Testing**
   ```bash
   mvn clean test -Dcucumber.options="--tags @smoke"
   ```

2. **Run Tests in Headless Mode for CI/CD**
   ```bash
   mvn clean test -Dheadless=true
   ```

3. **Monitor Execution with Verbose Output**
   ```bash
   mvn clean test -X  # X for extra verbose
   ```

4. **Quick Test Validation**
   ```bash
   mvn clean test -Dcucumber.options="--dry-run"
   ```

5. **Generate Report Only (No Execution)**
   ```bash
   mvn surefire-report:report
   ```

6. **Clear All Generated Files**
   ```bash
   mvn clean
   ```

---

## 🚀 Execute Now!

### Get Started in 3 Commands
```bash
# 1. Install dependencies
mvn clean install

# 2. Run tests
mvn clean test

# 3. Open report
# Navigate to: target/cucumber-reports/cucumber.html
```

---

**Happy Testing! 🎉**

Last Updated: June 2, 2026

