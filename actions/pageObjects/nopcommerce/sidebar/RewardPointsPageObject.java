package pageObjects.nopcommerce.sidebar;

import org.openqa.selenium.WebDriver;

public class RewardPointsPageObject extends SidebarPageObject {
    private WebDriver driver;

    public RewardPointsPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
