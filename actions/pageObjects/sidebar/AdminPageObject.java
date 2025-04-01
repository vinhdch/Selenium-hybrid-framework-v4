package pageObjects.sidebar;

import PageUIs.sidebar.AdminPageUI;
import org.openqa.selenium.WebDriver;
import pageObjects.PageGeneratorManager;

public class AdminPageObject extends SidebarPageObject {
    private WebDriver driver;

    public AdminPageObject(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    public AdminPageObject OpenAdminProductMenu() {
        String attributeValue = getElementAttribute(AdminPageUI.ADMIN_PRODUCT_MENU, "class");

        if (!attributeValue.endsWith("menu-is-opening menu-open")) {
            waitForElementClickable(AdminPageUI.ADMIN_PRODUCT_MENU);
            clickToElement(AdminPageUI.ADMIN_PRODUCT_MENU);
        }

        waitForElementClickable(AdminPageUI.ADMIN_PRODUCT_SUBMENU);
        clickToElement(AdminPageUI.ADMIN_PRODUCT_SUBMENU);

        return PageGeneratorManager.getPageInstance(AdminPageObject.class, driver);
    }

}
