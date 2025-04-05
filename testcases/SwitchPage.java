import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.PageGeneratorManager;
import pageObjects.RegisterPageObject;
import pageObjects.sidebar.AddressesPageObject;
import pageObjects.sidebar.CustomerInfoPageObject;
import pageObjects.sidebar.OrdersPageObject;
import pageObjects.sidebar.RewardPointsPageObject;

public class SwitchPage extends BaseTest {
    WebDriver driver;
    HomePageObject homePage;
    LoginPageObject loginPage;
    RegisterPageObject registerPage;
    CustomerInfoPageObject customerInfoPage;
    AddressesPageObject addressesPage;
    RewardPointsPageObject rewardPointsPage;
    OrdersPageObject ordersPage;
    String firstname, lastname, email, password;

    @Parameters({"url", "browserName"})
    @BeforeClass
    public void beforeClass(String urlValue, String browser) {
        driver = getBrowserDriverName(urlValue, browser);

        firstname = "John";
        lastname = "Nathan";
        email = "orangehrm" + generateFakeNumber() + "@gmail.com";
        password = "qwerp2345$%$%0";

        homePage = PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
    }

    @Test
    public void TC_01_Register() {

        registerPage = (RegisterPageObject) homePage.clickToHeaderLink("register");

        registerPage.enterToTextbox(firstname, "FirstName");
        registerPage.enterToTextbox(lastname, "LastName");
        registerPage.enterToTextbox(email, "Email");
        registerPage.enterToTextbox(password, "Password");
        registerPage.enterToTextbox(password, "ConfirmPassword");


        registerPage.clickToRegisterButton();

        log.info("TC_01_Register: get Register Success Message");
        verifyEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed!!!!!");

        homePage = registerPage.clickLogoutLink();
    }

    @Test
    public void TC_02_Login() {

        loginPage = (LoginPageObject) homePage.clickToHeaderLink("login");

        homePage = loginPage.login(email, password);
    }

    @Test
    public void TC_03_CustomerInfo() {

        customerInfoPage = (CustomerInfoPageObject) homePage.clickToHeaderLink("account");

        log.info("TC_03_CustomerInfo: get first name value textbox");
        verifyEquals(customerInfoPage.getTextBoxValue("FirstName"), firstname);

        log.info("TC_03_CustomerInfo: get last name value textbox");
        verifyEquals(customerInfoPage.getTextBoxValue("LastName"), firstname);

        log.info("TC_03_CustomerInfo: get email value name textbox");
        verifyEquals(customerInfoPage.getTextBoxValue("Email"), email);
    }

    @Test
    public void TC_04_SwitchPage() {

        // customer -> address
        addressesPage = (AddressesPageObject) customerInfoPage.openSideMenuByPageName("Addresses");

        // address -> Reward points
        rewardPointsPage = (RewardPointsPageObject) addressesPage.openSideMenuByPageName("Reward points");

        // Reward points -> orders
        ordersPage = (OrdersPageObject) rewardPointsPage.openSideMenuByPageName("Orders");

        // order -> customer
        customerInfoPage = (CustomerInfoPageObject) ordersPage.openSideMenuByPageName("Customer info");

        // customer -> Reward points
        rewardPointsPage = (RewardPointsPageObject) customerInfoPage.openSideMenuByPageName("Reward points");
    }

    @AfterClass
    public void afterClass() {
        quitBrowser();
    }
}
