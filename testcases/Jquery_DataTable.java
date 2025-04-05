import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.PageGeneratorManager;

public class Jquery_DataTable extends BaseTest {
    WebDriver driver;
    HomePageObject homePage;

    @Parameters({"url", "browserName"})
    @BeforeClass
    public void beforeClass(String urlValue, String browser) {
        driver = getBrowserDriverName(urlValue, browser);

        homePage = PageGeneratorManager.getPageInstance(HomePageObject.class, driver);
    }

    @Test
    public void TC_01_Pagination() {
        homePage.openPageByNumber("2");
        assertTrue(homePage.pageIsActive("2"));

        homePage.enterToTextboxByHeaderName("Country", "ASIA");
        assertTrue(homePage.isRowValueDisplayed("country", "ASIA"));

        homePage.enterToTextboxByHeaderName("Males", "803");
        assertTrue(homePage.isRowValueDisplayed("males", "803"));
    }

    @AfterClass
    public void afterClass() {
        quitBrowser();
    }
}
