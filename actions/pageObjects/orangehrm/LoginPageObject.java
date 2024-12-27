package pageObjects.orangehrm;

import PageUIs.orangehrm.LoginPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends BasePage {
    private WebDriver driver;

    public LoginPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void enterToUsernameTextbox(String userName) {
        waitForElementVisible(driver, LoginPageUI.USERNAME_TEXTBOX);
        sendKeyToElement(driver, LoginPageUI.USERNAME_TEXTBOX, userName);
    }

    public void enterToPasswordTextbox(String password) {
        waitForElementVisible(driver, LoginPageUI.PASSWORD_TEXTBOX);
        sendKeyToElement(driver, LoginPageUI.PASSWORD_TEXTBOX, password);
    }

    public void clickLoginButton() {
        waitForElementClickable(driver, LoginPageUI.LOGIN_BUTTON);
        clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
    }
}
