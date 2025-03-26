package pageObjects.nopcommerce.sidebar;

import PageUIs.nopcommerce.sidebar.SidebarPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.PageGeneratorManager;

public class SidebarPageObject extends BasePage {
    private WebDriver driver;

    public SidebarPageObject(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    public Object openSideMenuByPageName(String pageName) {
        waitForElementClickable(SidebarPageUI.DYNAMIC_SIDE_MENU_LINK, pageName);
        clickToElement(SidebarPageUI.DYNAMIC_SIDE_MENU_LINK, pageName);

        return switch (pageName) {
            case "Reward points" -> PageGeneratorManager.getPageInstance(RewardPointsPageObject.class, driver);
            case "Addresses" -> PageGeneratorManager.getPageInstance(AddressesPageObject.class, driver);
            case "Customer info" -> PageGeneratorManager.getPageInstance(CustomerInfoPageObject.class, driver);
            case "Orders" -> PageGeneratorManager.getPageInstance(OrdersPageObject.class, driver);
            default -> null;
        };
    }
}
