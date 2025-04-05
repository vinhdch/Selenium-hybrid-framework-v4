import commons.BaseTest;
import commons.GlobalConstants;
import org.openqa.selenium.WebDriver;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.PageGeneratorManager;

public class Jquery_UploadFile extends BaseTest {
    WebDriver driver;
    HomePageObject homePage;
    String img1, img2, img3, img4;

    @Parameters({"url", "browserName"})
    @BeforeClass
    public void beforeClass(String urlValue, String browser) {
        driver = getBrowserDriverName(urlValue, browser);

        homePage = PageGeneratorManager.getPageInstance(HomePageObject.class, driver);

        img1 = "img1.jpg";
        img2 = "img2.jpg";
        img3 = "img3.jpg";
        img4 = "img4.jpg";
    }

    @Test
    public void TC_01_Upload_File() {
        homePage.uploadMultipleFiles(img1, img2, img3, img4);
        homePage.sleepInSecond(GlobalConstants.THREAD_TIMEOUT);


        assertTrue(homePage.isFileUploadedByName(img1));
        assertTrue(homePage.isFileUploadedByName(img2));
        assertTrue(homePage.isFileUploadedByName(img3));
        assertTrue(homePage.isFileUploadedByName(img4));


        homePage.clickToUploadButton();

        assertTrue(homePage.isFileUploadedSuccess(img1));
        assertTrue(homePage.isFileUploadedSuccess(img2));
        assertTrue(homePage.isFileUploadedSuccess(img3));
        assertTrue(homePage.isFileUploadedSuccess(img4));

    }

    @AfterClass
    public void afterClass() {
        quitBrowser();
    }
}
