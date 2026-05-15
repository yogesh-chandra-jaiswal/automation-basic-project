package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;  // ADD THIS IMPORT

public class HomePage {

    @FindBy(xpath = "//*[@id='user-name']")
    WebElement username;

    @FindBy(xpath = "//*[@id='password']")
    WebElement password;

    @FindBy(xpath = "//*[@id='login-button']")
    WebElement loginBtn;

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // ADD THIS LINE
    }

    public void open(String url) {
        driver.get(url);
    }

    public void signIn(){
        username.click();
        username.sendKeys("standard_user");

        password.click();
        password.sendKeys("secret_sauce");

        loginBtn.click();
    }
}