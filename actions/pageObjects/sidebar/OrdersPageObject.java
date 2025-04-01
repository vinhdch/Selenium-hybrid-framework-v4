package pageObjects.sidebar;

import org.openqa.selenium.WebDriver;

public class OrdersPageObject extends SidebarPageObject {
    private WebDriver driver;

    public OrdersPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


}
