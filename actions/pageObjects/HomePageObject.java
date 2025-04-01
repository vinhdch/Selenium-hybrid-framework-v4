package pageObjects;

import PageUIs.HomePageUI;
import commons.BasePage;
import commons.GlobalConstants;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.sidebar.CustomerInfoPageObject;

import java.util.ArrayList;
import java.util.List;

public class HomePageObject extends BasePage {
    private WebDriver driver;

    public HomePageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public Object clickToHeaderLink(String headerLink) {
        waitForElementClickable(HomePageUI.DYNAMIC_HEADER_LINK, headerLink);
        clickToElement(HomePageUI.DYNAMIC_HEADER_LINK, headerLink);
        switch (headerLink) {
            case "register" :
                return PageGeneratorManager.getPageInstance(RegisterPageObject.class, driver);
            case "login" :
                return PageGeneratorManager.getPageInstance(LoginPageObject.class, driver);
            case "account" :
                return PageGeneratorManager.getPageInstance(CustomerInfoPageObject.class, driver);
            default:
                return null;
        }
    }

    public void openPageByNumber(String pageNumber) {
        waitForElementClickable(HomePageUI.JQUERY_DYNAMIC_PAGE_LINK, pageNumber);
        clickToElement(HomePageUI.JQUERY_DYNAMIC_PAGE_LINK, pageNumber);
    }

    public boolean pageIsActive(String pageNumber) {
        waitForElementVisible(HomePageUI.JQUERY_DYNAMIC_PAGE_LINK, pageNumber);
        return getElementAttribute(HomePageUI.JQUERY_DYNAMIC_PAGE_LINK, "class", pageNumber).endsWith("active");
    }

    public void enterToTextboxByHeaderName(String headerName, String inputValue) {
        refreshCurrentPage();
        waitForElementVisible(HomePageUI.JQUERY_DYNAMIC_HEADER_LINK, headerName);
        sendKeyToElement(HomePageUI.JQUERY_DYNAMIC_HEADER_LINK, inputValue, headerName);
        sendKeyBoardToElement(HomePageUI.JQUERY_DYNAMIC_HEADER_LINK, Keys.ENTER, headerName);
    }

    public boolean isRowValueDisplayed(String rowName, String inputValue) {
        waitForElementVisible(HomePageUI.JQUERY_DYNAMIC_ROW, rowName, inputValue);
        return isElementDisplayed(HomePageUI.JQUERY_DYNAMIC_ROW, rowName, inputValue);
    }

    public List<String> getAllValueAtColumnName(String columnName) {
        int columnIndexNumber = getListWebElement(HomePageUI.JQUERY_DYNAMIC_COLUMN_HEADER, columnName).size() +1;

        String columnIndex = String.valueOf(columnIndexNumber);

        List<WebElement> allElements = getListWebElement(HomePageUI.JQUERY_DYNAMIC_ALL_VALUE_BY_COLUMN_INDEX, columnIndex);

        List<String> allTextValue = new ArrayList<String>();

        for (WebElement element : allElements) {
            allTextValue.add(element.getText());
        }

        return allTextValue;
    }

    public boolean isFileUploadedByName(String fileName) {
        waitForElementVisible(HomePageUI.JQUERY_FILE_UPLOADED_BY_FILE_NAME, fileName);
        return isElementDisplayed(HomePageUI.JQUERY_FILE_UPLOADED_BY_FILE_NAME, fileName);
    }

    public void clickToUploadButton() {
        List<WebElement> startButtons = getListWebElement(HomePageUI.JQUERY_UPLOAD_BUTTON);
        for (WebElement button : startButtons) {
            button.click();
            sleepInSecond(GlobalConstants.THREAD_TIMEOUT);
        }
    }

    public boolean isFileUploadedSuccess(String fileName) {
        waitForElementVisible(HomePageUI.JQUERY_FILE_UPLOADED_SUCCESS, fileName);
        return isElementDisplayed(HomePageUI.JQUERY_FILE_UPLOADED_SUCCESS, fileName);
    }
}
