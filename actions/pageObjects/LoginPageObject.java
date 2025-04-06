package pageObjects;

import PageUIs.LoginPageUI;
import commons.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends BasePage {
    private WebDriver driver;

    public LoginPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("input value into email textbox: {0}")
    public void enterEmailTextbox(String emailAddress) {
        waitForAllElementVisible(LoginPageUI.EMAIL_TEXT_BOX);
        sendKeyToElement(LoginPageUI.EMAIL_TEXT_BOX, emailAddress);
    }

    @Step("input value into pw textbox: {0}")
    public void enterPasswordTextbox(String password) {
        waitForAllElementVisible(LoginPageUI.PASSWORD_TEXT_BOX);
        sendKeyToElement(LoginPageUI.PASSWORD_TEXT_BOX, password);
    }

    @Step("click login button")
    public HomePageObject clickLoginButton() {
        waitForElementVisible(LoginPageUI.LOGIN_BUTTON);
        clickToElement(LoginPageUI.LOGIN_BUTTON);
        return PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
    }

    @Step("login: {0} and {1}")
    public HomePageObject login(String emailAddress, String password) {
        enterEmailTextbox(emailAddress);
        enterPasswordTextbox(password);
        clickLoginButton();
        return PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
    }
}
