package com.nopcommerce.users;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Level_2_Apply_BasePage_II extends BasePage {
    WebDriver driver;
    String emailAddress = "vinhTest1h@gmail.com";
    BasePage basePage;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();

        basePage = BasePage.getBasePage();

        openPageUrl(driver, "http://localhost/");
    }

    @Test
    public void TC_01_Register_With_Empty_Data() {
        clickToElement(driver, "//a[@class='ico-register']");
        clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(getElementText(driver, "//span[@id='FirstName-error']"), "First name is required.");
        Assert.assertEquals(getElementText(driver, "//span[@id='LastName-error']"), "Last name is required.");
        Assert.assertEquals(getElementText(driver, "//span[@id='Email-error']"), "Email is required.");
        Assert.assertEquals(getElementText(driver, "//span[@id='ConfirmPassword-error']"), "Password is required.");
    }

    @Test
    public void TC_01_Register_With_Invalid_Email() {
        clickToElement(driver, "//a[@class='ico-register']");

        sendKeyToElement(driver, "//input[@id='FirstName']", "vinhTest");
        sendKeyToElement(driver, "//input[@id='LastName']", "vinhTest");
        sendKeyToElement(driver, "//input[@id='Email']", "vinhTest%&&&*");
        sendKeyToElement(driver, "//input[@id='Password']", "123456");
        sendKeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");

        clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(getElementText(driver, "//span[@id='Email-error']"), "Please enter a valid email address.");
    }

    @Test
    public void TC_01_Register_Success() {
        clickToElement(driver, "//a[@class='ico-register']");

        sendKeyToElement(driver, "//input[@id='FirstName']", "vinhTest");
        sendKeyToElement(driver, "//input[@id='LastName']", "vinhTest");
        sendKeyToElement(driver, "//input[@id='Email']", emailAddress);
        sendKeyToElement(driver, "//input[@id='Password']", "123456");
        sendKeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");

        clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(getElementText(driver, "//div[@class='result']"), "Your registration completed");

        clickToElement(driver, "//a[@class='ico-logout']");
    }


    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
