package com.nopcommerce.users;

import commons.BasePage;
import commons.BaseTest;
import commons.PageManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.CustomerInfoPageObject;
import pageObjects.nopcommerce.HomePageObject;
import pageObjects.nopcommerce.LoginPageObject;
import pageObjects.nopcommerce.RegisterPageObject;

import java.util.Random;

public class Level_4_PageFactory extends BaseTest {
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

        homePage = (HomePageObject) PageManager.getPage(driver, "HomePage");

        firstname = "John";
        lastname = "Nathan";
        email = "kangugu" + generateFakeNumber() +"@gmail.com";
        password = "qwertyuiop2345$%$%";


    }

    @Test
    public void TC_01_Register() {
        homePage.clickToRegisterLink();

        registerPage = new RegisterPageObject(driver);

        registerPage.enterFirstNameTextbox(firstname);
        registerPage.enterLastNameTextbox(lastname);
        registerPage.enterEmailTextbox(email);
        registerPage.enterPasswordTextbox(password);
        registerPage.enterConfirmPasswordTextbox(password);
        registerPage.clickToRegisterButton();

        Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

        registerPage.clickLogoutLink();

        homePage = (HomePageObject) PageManager.getPage(driver, "HomePage");
    }

    @Test
    public void TC_02_Login() {
        homePage.clickLoginLink();

        loginPage = (LoginPageObject) PageManager.getPage(driver,"LoginPage");

        loginPage.enterEmailTextbox(email);
        loginPage.enterPasswordTextbox(password);
        loginPage.clickLoginButton();

        homePage = (HomePageObject) PageManager.getPage(driver, "HomePage");
    }

    @Test
    public void TC_03_CustomerInfo() {
        homePage.clickMyAccountLink();

        customerInfoPage = (CustomerInfoPageObject) PageManager.getPage(driver, "CustomerInfoPage");

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
