package pageObjects.orangehrm;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class DashboardPageObject extends BasePage {
    private WebDriver driver;

    public DashboardPageObject(WebDriver driver) {
        this.driver = driver;
    }
}
