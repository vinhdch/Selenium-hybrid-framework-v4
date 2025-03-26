package pageObjects.nopcommerce.sidebar;

import PageUIs.nopcommerce.sidebar.CustomerInfoPageUI;
import org.openqa.selenium.WebDriver;

public class CustomerInfoPageObject extends SidebarPageObject {
    private WebDriver driver;

    public CustomerInfoPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public String getTextBoxValue(String textboxName) {
        waitForElementVisible(CustomerInfoPageUI.DYNAMIC_TEXT_BOX, textboxName);
        return getElementAttribute(CustomerInfoPageUI.DYNAMIC_TEXT_BOX, "value", textboxName);
    }
}
