package pageObjects.nopcommerce;

import PageUIs.nopcommerce.RegisterPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class RegisterPageObject extends BasePage {
    private WebDriver driver;

    public RegisterPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void enterFirstNameTextbox(String firstname) {
        waitForAllElementVisible(RegisterPageUI.FIRST_NAME_TEXT_BOX);
        sendKeyToElement(RegisterPageUI.FIRST_NAME_TEXT_BOX, firstname);
    }

    public void enterLastNameTextbox(String lastname) {
        waitForAllElementVisible(RegisterPageUI.LAST_NAME_TEXT_BOX);
        sendKeyToElement(RegisterPageUI.LAST_NAME_TEXT_BOX, lastname);
    }

    public void enterEmailTextbox(String emailAddress) {
        waitForAllElementVisible(RegisterPageUI.EMAIL_TEXT_BOX);
        sendKeyToElement(RegisterPageUI.EMAIL_TEXT_BOX, emailAddress);
    }

    public void enterPasswordTextbox(String password) {
        waitForAllElementVisible(RegisterPageUI.PASSWORD_TEXT_BOX);
        sendKeyToElement(RegisterPageUI.PASSWORD_TEXT_BOX, password);
    }

    public void enterConfirmPasswordTextbox(String password) {
        waitForAllElementVisible(RegisterPageUI.CONFIRM_PASSWORD_TEXT_BOX);
        sendKeyToElement(RegisterPageUI.CONFIRM_PASSWORD_TEXT_BOX, password);
    }

    public void clickToRegisterButton() {
        waitForElementClickable(RegisterPageUI.REGISTER_BUTTON);
        clickToElement(RegisterPageUI.REGISTER_BUTTON);
    }

    public String getRegisterSuccessMessage() {
        waitForElementVisible(RegisterPageUI.REGISTER_SUCCESS_MESSAGE);
        return getElementText(RegisterPageUI.REGISTER_SUCCESS_MESSAGE);
    }

    public HomePageObject clickLogoutLink() {
        waitForElementClickable(RegisterPageUI.LOGOUT_LINK);
        clickToElement(RegisterPageUI.LOGOUT_LINK);
        return PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
    }
}
