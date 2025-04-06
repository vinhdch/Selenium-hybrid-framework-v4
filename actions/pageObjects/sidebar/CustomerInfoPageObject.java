package pageObjects.sidebar;

import PageUIs.sidebar.CustomerInfoPageUI;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class CustomerInfoPageObject extends SidebarPageObject {
    private WebDriver driver;

    public CustomerInfoPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("get textbox value: {0}")
    public String getTextBoxValue(String textboxName) {
        waitForElementVisible(CustomerInfoPageUI.DYNAMIC_TEXT_BOX, textboxName);
        return getElementAttribute(CustomerInfoPageUI.DYNAMIC_TEXT_BOX, "value", textboxName);
    }
}
