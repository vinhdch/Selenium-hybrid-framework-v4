package pageObjects;

import PageUIs.HomePageUI;
import commons.BasePage;
import commons.GlobalConstants;
import io.qameta.allure.Step;
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

    @Step("click header link: {0}")
    public Object clickToHeaderLink(String headerLink) {
        waitForElementClickable(HomePageUI.DYNAMIC_HEADER_LINK, headerLink);
        clickToElement(HomePageUI.DYNAMIC_HEADER_LINK, headerLink);
        return switch (headerLink) {
            case "register" -> PageGeneratorManager.getPageInstance(RegisterPageObject.class, driver);
            case "login" -> PageGeneratorManager.getPageInstance(LoginPageObject.class, driver);
            case "account" -> PageGeneratorManager.getPageInstance(CustomerInfoPageObject.class, driver);
            default -> null;
        };
    }

    @Step("open page number: {0}")
    public void openPageByNumber(String pageNumber) {
        waitForElementClickable(HomePageUI.JQUERY_DYNAMIC_PAGE_LINK, pageNumber);
        clickToElement(HomePageUI.JQUERY_DYNAMIC_PAGE_LINK, pageNumber);
    }

    @Step("page is active: {0}")
    public boolean pageIsActive(String pageNumber) {
        waitForElementVisible(HomePageUI.JQUERY_DYNAMIC_PAGE_LINK, pageNumber);
        return getElementAttribute(HomePageUI.JQUERY_DYNAMIC_PAGE_LINK, "class", pageNumber).endsWith("active");
    }

    @Step("input value to textbox: {0} and {1}")
    public void enterToTextboxByHeaderName(String headerName, String inputValue) {
        refreshCurrentPage();
        waitForElementVisible(HomePageUI.JQUERY_DYNAMIC_HEADER_LINK, headerName);
        sendKeyToElement(HomePageUI.JQUERY_DYNAMIC_HEADER_LINK, inputValue, headerName);
        sendKeyBoardToElement(HomePageUI.JQUERY_DYNAMIC_HEADER_LINK, Keys.ENTER, headerName);
    }

    @Step("verify row value: {0} and {1}")
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

    @Step("verify file is uploaded: {0}")
    public boolean isFileUploadedByName(String fileName) {
        waitForElementVisible(HomePageUI.JQUERY_FILE_UPLOADED_BY_FILE_NAME, fileName);
        return isElementDisplayed(HomePageUI.JQUERY_FILE_UPLOADED_BY_FILE_NAME, fileName);
    }

    @Step("click upload button")
    public void clickToUploadButton() {
        List<WebElement> startButtons = getListWebElement(HomePageUI.JQUERY_UPLOAD_BUTTON);
        for (WebElement button : startButtons) {
            button.click();
            sleepInSecond(GlobalConstants.THREAD_TIMEOUT);
        }
    }

    @Step("verify file is upload success: {0}")
    public boolean isFileUploadedSuccess(String fileName) {
        waitForElementVisible(HomePageUI.JQUERY_FILE_UPLOADED_SUCCESS, fileName);
        return isElementDisplayed(HomePageUI.JQUERY_FILE_UPLOADED_SUCCESS, fileName);
    }
}
