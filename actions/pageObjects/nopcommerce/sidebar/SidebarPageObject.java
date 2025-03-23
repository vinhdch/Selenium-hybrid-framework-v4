package pageObjects.nopcommerce.sidebar;

import PageUIs.nopcommerce.sidebar.SidebarPageUI;
import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.PageGeneratorManager;

public class SidebarPageObject extends BasePage {
    WebDriver driver;

    public SidebarPageObject(WebDriver driver) {
        this.driver=driver;
    }

    public AddressesPageObject OpenAddressPage(WebDriver driver) {
        waitForElementVisible(driver, SidebarPageUI.ADDRESSES_LINK);
        clickToElement(driver, SidebarPageUI.ADDRESSES_LINK);
        return PageGeneratorManager.getPageInstance(AddressesPageObject.class, driver);
    }

    public RewardPointsPageObject OpenRewardPointsPage(WebDriver driver) {
        waitForElementVisible(driver, SidebarPageUI.REWARD_POINTS_LINK);
        clickToElement(driver, SidebarPageUI.REWARD_POINTS_LINK);
        return PageGeneratorManager.getPageInstance(RewardPointsPageObject.class, driver);
    }


    public CustomerInfoPageObject OpenCustomerInfoPage(WebDriver driver) {
        waitForElementVisible(driver, SidebarPageUI.CUSTOMER_INFO_LINK);
        clickToElement(driver, SidebarPageUI.CUSTOMER_INFO_LINK);
        return PageGeneratorManager.getPageInstance(CustomerInfoPageObject.class, driver);
    }

    public OrdersPageObject OpenOrdersPage(WebDriver driver) {
        waitForElementVisible(driver, SidebarPageUI.ORDERS_LINK);
        clickToElement(driver, SidebarPageUI.ORDERS_LINK);
        return PageGeneratorManager.getPageInstance(OrdersPageObject.class, driver);
    }
}
