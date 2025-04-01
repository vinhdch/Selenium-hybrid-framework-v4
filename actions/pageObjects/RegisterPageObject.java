package pageObjects;

import PageUIs.RegisterPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class RegisterPageObject extends BasePage {
    private WebDriver driver;

    public RegisterPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void enterToTextbox(String inputValue, String textboxName) {
        waitForAllElementVisible(RegisterPageUI.DYNAMIC_TEXT_BOX, textboxName);
        sendKeyToElement(RegisterPageUI.DYNAMIC_TEXT_BOX, inputValue, textboxName);
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
