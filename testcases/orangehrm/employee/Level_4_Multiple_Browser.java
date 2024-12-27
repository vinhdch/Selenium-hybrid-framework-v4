package orangehrm.employee;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.orangehrm.DashboardPageObject;
import pageObjects.orangehrm.LoginPageObject;

public class Level_4_Multiple_Browser extends BaseTest {

    private WebDriver driver;
    private LoginPageObject loginPage;
    private DashboardPageObject dashboardPage;

    private String userName, password;

    @Parameters({"url", "browserName"})
    @BeforeClass
    public void beforeClass(String urlValue, String browser) {

        driver = getBrowserDriverName(urlValue, browser);
        loginPage = new LoginPageObject(driver);

        userName = "Admin";
        password = "admin123";
    }

    @Test
    public void TC_01_Login() {
        loginPage.enterToUsernameTextbox(userName);
        loginPage.enterToPasswordTextbox(password);
        loginPage.clickLoginButton();

        dashboardPage = new DashboardPageObject(driver);

    }

    @Test
    public void TC_02_New_Employee() {

    }

    @Test
    public void TC_03_Personal_Detail() {

    }

    @AfterClass
    public void afterClass() {
       quitBrowser();
    }
}
