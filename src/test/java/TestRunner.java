import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * TestRunner - Main test runner class for Cucumber BDD tests
 * Configures Cucumber execution with feature files and plugins
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/featuresFiles",
        glue = {"steps", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        stepNotifications = true,
        dryRun = false,
        publish = false
)
public class TestRunner {
}