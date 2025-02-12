package com.nopcommerce.users;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.*;

import java.util.Random;

public class Level_6_PageGenerator_II extends BaseTest {
    WebDriver driver;
    HomePageObject homePage;
    LoginPageObject loginPage;
    RegisterPageObject registerPage;
    CustomerInfoPageObject customerInfoPage;

    String firstname, lastname, email, password;

    @Parameters({"url", "browserName"})
    @BeforeClass
    public void beforeClass(String urlValue, String browser) {
        driver = getBrowserDriverName(urlValue, browser);

        firstname = "John";
        lastname = "Nathan";
        email = "kangugu" + generateFakeNumber() +"@gmail.com";
        password = "qwertyuiop2345$%$%0";

        homePage = PageGeneratorManager.getHomePage(driver);
    }

    @Test
    public void TC_01_Register() {

        registerPage = homePage.clickToRegisterLink();;

        registerPage.enterFirstNameTextbox(firstname);
        registerPage.enterLastNameTextbox(lastname);
        registerPage.enterEmailTextbox(email);
        registerPage.enterPasswordTextbox(password);
        registerPage.enterConfirmPasswordTextbox(password);
        registerPage.clickToRegisterButton();

        Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

        homePage = registerPage.clickLogoutLink();
    }

    @Test
    public void TC_02_Login() {

        loginPage = homePage.clickLoginLink();

        loginPage.enterEmailTextbox(email);
        loginPage.enterPasswordTextbox(password);

        homePage = loginPage.clickLoginButton();
    }

    @Test
    public void TC_03_CustomerInfo() {

        customerInfoPage = homePage.clickMyAccountLink();;

        Assert.assertEquals(customerInfoPage.getFirstNameValue(), firstname);
        Assert.assertEquals(customerInfoPage.getLastNameValue(), lastname);
        Assert.assertEquals(customerInfoPage.getEmailValue(), email);
    }

    public int generateFakeNumber() {
        Random ran = new Random();
        return ran.nextInt(99999);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
