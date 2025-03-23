package pageObjects.nopcommerce;

import PageUIs.nopcommerce.HomePageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.sidebar.CustomerInfoPageObject;

public class HomePageObject extends BasePage {
    private WebDriver driver;

    public HomePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public RegisterPageObject clickToRegisterLink() {
        waitForElementClickable(driver, HomePageUI.REGISTER_LINK);
        clickToElement(driver, HomePageUI.REGISTER_LINK);
        return PageGeneratorManager.getPageInstance(RegisterPageObject.class, driver);
    }

    public LoginPageObject clickLoginLink() {
        waitForElementClickable(driver, HomePageUI.LOGIN_LINK);
        clickToElement(driver, HomePageUI.LOGIN_LINK);
        return PageGeneratorManager.getPageInstance(LoginPageObject.class, driver);
    }

    public CustomerInfoPageObject clickMyAccountLink() {
        waitForElementClickable(driver, HomePageUI.MY_ACCOUNT_LINK);
        clickToElement(driver, HomePageUI.MY_ACCOUNT_LINK);
        return PageGeneratorManager.getPageInstance(CustomerInfoPageObject.class, driver);
    }
}
