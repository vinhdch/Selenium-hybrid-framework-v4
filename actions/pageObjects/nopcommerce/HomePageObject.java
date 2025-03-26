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

    public Object clickToHeaderLink(String headerLink) {
        waitForElementClickable(HomePageUI.DYNAMIC_HEADER_LINK, headerLink);
        clickToElement(HomePageUI.DYNAMIC_HEADER_LINK, headerLink);
        switch (headerLink) {
            case "register" :
                return PageGeneratorManager.getPageInstance(RegisterPageObject.class, driver);
            case "login" :
                return PageGeneratorManager.getPageInstance(LoginPageObject.class, driver);
            case "account" :
                return PageGeneratorManager.getPageInstance(CustomerInfoPageObject.class, driver);
            default:
                return null;
        }
    }
}
