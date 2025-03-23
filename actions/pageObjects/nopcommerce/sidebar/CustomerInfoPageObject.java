package pageObjects.nopcommerce.sidebar;

import PageUIs.nopcommerce.sidebar.CustomerInfoPageUI;
import org.openqa.selenium.WebDriver;

public class CustomerInfoPageObject extends SidebarPageObject {

    public CustomerInfoPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public String getFirstNameValue() {
        waitForElementVisible(CustomerInfoPageUI.FIRST_NAME_TEXT_BOX);
        return getElementAttribute(CustomerInfoPageUI.FIRST_NAME_TEXT_BOX, "value");
    }

    public String getLastNameValue() {
        waitForElementVisible(CustomerInfoPageUI.LAST_NAME_TEXT_BOX);
        return getElementAttribute(CustomerInfoPageUI.LAST_NAME_TEXT_BOX, "value");
    }

    public String getEmailValue() {
        waitForElementVisible(CustomerInfoPageUI.EMAIL_TEXT_BOX);
        return getElementAttribute(CustomerInfoPageUI.EMAIL_TEXT_BOX, "value");
    }
}
