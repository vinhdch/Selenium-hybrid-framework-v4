package com.nopcommerce.users;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Level_1_Apply_BasePage_I {
    WebDriver driver;
    String emailAddress = "vinhTest123444@gmail.com";
    BasePage basePage;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();

        basePage = new BasePage();
       basePage.openPageUrl(driver, "http://localhost/");
    }

    @Test
    public void TC_01_Register_With_Empty_Data() {
        basePage.clickToElement(driver, "//a[@class='ico-register']");
        basePage.clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(basePage.getElementText(driver, "//span[@id='FirstName-error']"), "First name is required.");
        Assert.assertEquals(basePage.getElementText(driver, "//span[@id='LastName-error']"), "Last name is required.");
        Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Email-error']"), "Email is required.");
        Assert.assertEquals(basePage.getElementText(driver, "//span[@id='ConfirmPassword-error']"), "Password is required.");
    }

    @Test
    public void TC_01_Register_With_Invalid_Email() {
        basePage.clickToElement(driver, "//a[@class='ico-register']");

        basePage.sendKeyToElement(driver, "//input[@id='FirstName']", "vinhTest");
        basePage.sendKeyToElement(driver, "//input[@id='LastName']", "vinhTest");
        basePage.sendKeyToElement(driver, "//input[@id='Email']", "vinhTest%&&&*");
        basePage.sendKeyToElement(driver, "//input[@id='Password']", "123456");
        basePage.sendKeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");

        basePage.clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(basePage.getElementText(driver, "//span[@id='Email-error']"), "Please enter a valid email address.");
    }

    @Test
    public void TC_01_Register_Success() {
        basePage.clickToElement(driver, "//a[@class='ico-register']");

        basePage.sendKeyToElement(driver, "//input[@id='FirstName']", "vinhTest");
        basePage.sendKeyToElement(driver, "//input[@id='LastName']", "vinhTest");
        basePage.sendKeyToElement(driver, "//input[@id='Email']", emailAddress);
        basePage.sendKeyToElement(driver, "//input[@id='Password']", "123456");
        basePage.sendKeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");

        basePage.clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(basePage.getElementText(driver, "//div[@class='result']"), "Your registration completed");

        basePage.clickToElement(driver, "//a[@class='ico-logout']");
    }


    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
