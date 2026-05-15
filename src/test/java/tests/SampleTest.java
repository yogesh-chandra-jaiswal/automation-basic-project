package tests;

import base.BaseTest;
import org.testng.Assert;
import pages.HomePage;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {

    @Test
    public void openGoogleTest() {
        HomePage home = new HomePage(driver);
        home.open("https://www.saucedemo.com/");
        home.signIn();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }
}