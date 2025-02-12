package pageObjects.nopcommerce;

import PageUIs.nopcommerce.RegisterPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class RegisterPageObject extends BasePage {
    private WebDriver driver;

    public RegisterPageObject (WebDriver driver) {
        this.driver = driver;
    }

    public void enterFirstNameTextbox(String firstname) {
        waitForAllElementVisible(driver, RegisterPageUI.FIRST_NAME_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.FIRST_NAME_TEXT_BOX, firstname);
    }

    public void enterLastNameTextbox(String lastname) {
        waitForAllElementVisible(driver, RegisterPageUI.LAST_NAME_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.LAST_NAME_TEXT_BOX, lastname);
    }

    public void enterEmailTextbox(String emailAddress) {
        waitForAllElementVisible(driver, RegisterPageUI.EMAIL_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.EMAIL_TEXT_BOX, emailAddress);
    }

    public void enterPasswordTextbox(String password) {
        waitForAllElementVisible(driver, RegisterPageUI.PASSWORD_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.PASSWORD_TEXT_BOX, password);
    }

    public void enterConfirmPasswordTextbox(String password) {
        waitForAllElementVisible(driver, RegisterPageUI.CONFIRM_PASSWORD_TEXT_BOX);
        sendKeyToElement(driver, RegisterPageUI.CONFIRM_PASSWORD_TEXT_BOX, password);
    }

    public void clickToRegisterButton() {
        waitForElementClickable(driver, RegisterPageUI.REGISTER_BUTTON);
        clickToElement(driver, RegisterPageUI.REGISTER_BUTTON);
    }

    public String getRegisterSuccessMessage() {
        waitForElementVisible(driver, RegisterPageUI.REGISTER_SUCCESS_MESSAGE);
        return getElementText(driver, RegisterPageUI.REGISTER_SUCCESS_MESSAGE);
    }

    public HomePageObject clickLogoutLink() {
        waitForElementClickable(driver, RegisterPageUI.LOGOUT_LINK);
        clickToElement(driver, RegisterPageUI.LOGOUT_LINK);
        return PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
    }
}
