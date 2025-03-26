package pageObjects.nopcommerce.sidebar;

import org.openqa.selenium.WebDriver;

public class AddressesPageObject extends SidebarPageObject {
    private WebDriver driver;

    public AddressesPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
