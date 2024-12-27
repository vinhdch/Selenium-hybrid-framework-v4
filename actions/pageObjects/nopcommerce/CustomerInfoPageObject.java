package pageObjects.nopcommerce;

import PageUIs.nopcommerce.CustomerInfoPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class CustomerInfoPageObject extends BasePage {
    private WebDriver driver;

    public CustomerInfoPageObject (WebDriver driver) {
        this.driver = driver;
    }

    public String getFirstNameValue() {
        waitForElementVisible(driver, CustomerInfoPageUI.FIRST_NAME_TEXT_BOX);
        return getElementAttribute(driver, CustomerInfoPageUI.FIRST_NAME_TEXT_BOX, "value");
    }

    public String getLastNameValue() {
        waitForElementVisible(driver, CustomerInfoPageUI.LAST_NAME_TEXT_BOX);
        return getElementAttribute(driver, CustomerInfoPageUI.LAST_NAME_TEXT_BOX, "value");
    }

    public String getEmailValue() {
        waitForElementVisible(driver, CustomerInfoPageUI.EMAIL_TEXT_BOX);
        return getElementAttribute(driver, CustomerInfoPageUI.EMAIL_TEXT_BOX, "value");
    }
}
