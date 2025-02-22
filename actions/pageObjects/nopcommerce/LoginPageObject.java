package pageObjects.nopcommerce;

import PageUIs.nopcommerce.LoginPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends BasePage {
    private WebDriver driver;

    public LoginPageObject (WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmailTextbox(String emailAddress) {
        waitForAllElementVisible(driver, LoginPageUI.EMAIL_TEXT_BOX);
        sendKeyToElement(driver, LoginPageUI.EMAIL_TEXT_BOX, emailAddress);
    }

    public void enterPasswordTextbox(String password) {
        waitForAllElementVisible(driver, LoginPageUI.PASSWORD_TEXT_BOX);
        sendKeyToElement(driver, LoginPageUI.PASSWORD_TEXT_BOX, password);
    }

    public HomePageObject clickLoginButton() {
        waitForElementVisible(driver, LoginPageUI.LOGIN_BUTTON);
        clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
        return PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
    }
    public LoginPageObject login(String emailAddress, String password) {
        enterEmailTextbox(emailAddress);
        enterPasswordTextbox(password);
        clickLoginButton();
        return PageGeneratorManager.getPageInstance(LoginPageObject.class, driver);
    }
}
