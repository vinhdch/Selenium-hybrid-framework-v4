package pageObjects.nopcommerce.sidebar;

import PageUIs.nopcommerce.sidebar.SidebarPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.PageGeneratorManager;

public class SidebarPageObject extends BasePage {
    WebDriver driver;

    public SidebarPageObject(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    public AddressesPageObject OpenAddressPage(WebDriver driver) {
        waitForElementVisible(SidebarPageUI.ADDRESSES_LINK);
        clickToElement(SidebarPageUI.ADDRESSES_LINK);
        return PageGeneratorManager.getPageInstance(AddressesPageObject.class, driver);
    }

    public RewardPointsPageObject OpenRewardPointsPage(WebDriver driver) {
        waitForElementVisible(SidebarPageUI.REWARD_POINTS_LINK);
        clickToElement(SidebarPageUI.REWARD_POINTS_LINK);
        return PageGeneratorManager.getPageInstance(RewardPointsPageObject.class, driver);
    }


    public CustomerInfoPageObject OpenCustomerInfoPage(WebDriver driver) {
        waitForElementVisible(SidebarPageUI.CUSTOMER_INFO_LINK);
        clickToElement(SidebarPageUI.CUSTOMER_INFO_LINK);
        return PageGeneratorManager.getPageInstance(CustomerInfoPageObject.class, driver);
    }

    public OrdersPageObject OpenOrdersPage(WebDriver driver) {
        waitForElementVisible(SidebarPageUI.ORDERS_LINK);
        clickToElement(SidebarPageUI.ORDERS_LINK);
        return PageGeneratorManager.getPageInstance(OrdersPageObject.class, driver);
    }
}
