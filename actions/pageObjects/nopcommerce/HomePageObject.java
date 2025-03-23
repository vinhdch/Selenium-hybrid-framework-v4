package pageObjects.nopcommerce;

import PageUIs.nopcommerce.HomePageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.sidebar.CustomerInfoPageObject;

public class HomePageObject extends BasePage {
    private WebDriver driver;

    public HomePageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public RegisterPageObject clickToRegisterLink() {
        waitForElementClickable(HomePageUI.REGISTER_LINK);
        clickToElement(HomePageUI.REGISTER_LINK);
        return PageGeneratorManager.getPageInstance(RegisterPageObject.class, driver);
    }

    public LoginPageObject clickLoginLink() {
        waitForElementClickable(HomePageUI.LOGIN_LINK);
        clickToElement(HomePageUI.LOGIN_LINK);
        return PageGeneratorManager.getPageInstance(LoginPageObject.class, driver);
    }

    public CustomerInfoPageObject clickMyAccountLink() {
        waitForElementClickable(HomePageUI.MY_ACCOUNT_LINK);
        clickToElement(HomePageUI.MY_ACCOUNT_LINK);
        return PageGeneratorManager.getPageInstance(CustomerInfoPageObject.class, driver);
    }
}
