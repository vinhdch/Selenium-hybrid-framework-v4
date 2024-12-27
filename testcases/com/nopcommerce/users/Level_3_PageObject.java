package com.nopcommerce.users;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.nopcommerce.CustomerInfoPageObject;
import pageObjects.nopcommerce.HomePageObject;
import pageObjects.nopcommerce.LoginPageObject;
import pageObjects.nopcommerce.RegisterPageObject;

import java.time.Duration;
import java.util.Random;

public class Level_3_PageObject extends BasePage {
    WebDriver driver;
    HomePageObject homePage;
    LoginPageObject loginPage;
    RegisterPageObject registerPage;
    CustomerInfoPageObject customerInfoPage;

    String firstname, lastname, email, password;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();

        driver.get("http://localhost/");

        homePage = new HomePageObject(driver);

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

        homePage = new HomePageObject(driver);
    }

    @Test
    public void TC_02_Login() {
        homePage.clickLoginLink();

        loginPage = new LoginPageObject(driver);

        loginPage.enterEmailTextbox(email);
        loginPage.enterPasswordTextbox(password);
        loginPage.clickLoginButton();

        homePage = new HomePageObject(driver);
    }

    @Test
    public void TC_03_CustomerInfo() {
        homePage.clickMyAccountLink();

        customerInfoPage = new CustomerInfoPageObject(driver);

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
