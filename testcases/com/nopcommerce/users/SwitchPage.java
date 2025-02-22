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

public class SwitchPage extends BaseTest {
    WebDriver driver;
    HomePageObject homePage;
    LoginPageObject loginPage;
    RegisterPageObject registerPage;
    CustomerInfoPageObject customerInfoPage;
    AddressesPageObject addressesPage;
    RewardPointsPageObject rewardPointsPage;
    OrdersPageObject ordersPage;
    String firstname, lastname, email, password;

    @Parameters({"url", "browserName"})
    @BeforeClass
    public void beforeClass(String urlValue, String browser) {
        driver = getBrowserDriverName(urlValue, browser);

        firstname = "John";
        lastname = "Nathan";
        email = "orangehrm" + generateFakeNumber() +"@gmail.com";
        password = "qwerp2345$%$%0";

        homePage = PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
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

    @Test
    public void TC_04_SwitchPage() {

        // customer -> address
         addressesPage = customerInfoPage.OpenAddressPage(driver);

        // address -> Reward points
        rewardPointsPage = addressesPage.OpenRewardPointsPage(driver);

        // Reward points -> orders
        ordersPage = rewardPointsPage.OpenOrdersPage(driver);

        // order -> customer
        customerInfoPage = ordersPage.OpenCustomerInfoPage(driver);

        // customer -> Reward points
        rewardPointsPage = customerInfoPage.OpenRewardPointsPage(driver);
    }

    @AfterClass
    public void afterClass() {
        quitBrowser();
    }
}
