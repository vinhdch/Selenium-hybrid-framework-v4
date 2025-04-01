package pageObjects;

import PageUIs.LoginPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends BasePage {
    private WebDriver driver;

    public LoginPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void enterEmailTextbox(String emailAddress) {
        waitForAllElementVisible(LoginPageUI.EMAIL_TEXT_BOX);
        sendKeyToElement(LoginPageUI.EMAIL_TEXT_BOX, emailAddress);
    }

    public void enterPasswordTextbox(String password) {
        waitForAllElementVisible(LoginPageUI.PASSWORD_TEXT_BOX);
        sendKeyToElement(LoginPageUI.PASSWORD_TEXT_BOX, password);
    }

    public HomePageObject clickLoginButton() {
        waitForElementVisible(LoginPageUI.LOGIN_BUTTON);
        clickToElement(LoginPageUI.LOGIN_BUTTON);
        return PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
    }

    public HomePageObject login(String emailAddress, String password) {
        enterEmailTextbox(emailAddress);
        enterPasswordTextbox(password);
        clickLoginButton();
        return PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
    }
}
