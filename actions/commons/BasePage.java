package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BasePage {
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    private String castLocator(String locator, String...value) {
        return String.format(locator, (Object[]) value);
    }

    protected void openPageUrl(String pageUrl) {
        driver.get(pageUrl);
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getPageUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageSource() {
        return driver.getPageSource();
    }

    protected void backToPage() {
        driver.navigate().back();
    }

    protected void forwardToPage() {
        driver.navigate().forward();
    }

    public void refreshCurrentPage() {
        driver.navigate().refresh();
    }

    protected Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    protected void setCookies(Set<Cookie> cookies) {

        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
        sleepInSecond(GlobalConstants.LONG_TIMEOUT);
    }

    private Alert waitToAlertPresent() {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.alertIsPresent());
    }

    protected void acceptAlert() {
        waitToAlertPresent().accept();
    }

    protected void cancelAlert() {
        waitToAlertPresent().dismiss();
    }

    protected void sendKeyToAlert(String value) {
        waitToAlertPresent().sendKeys(value);
    }

    protected String getAlertText() {
        return waitToAlertPresent().getText();
    }

    public void switchToWindowByID(String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parentID)) {
                driver.switchTo().window(runWindow);
                break;
            }
        }
    }

    protected void switchToWindowByTitle(String title) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            driver.switchTo().window(runWindows);
            String currentWin = driver.getTitle();
            if (currentWin.equals(title)) {
                break;
            }
        }
    }

    protected void closeAllWindowsWithoutParent(String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            if (!runWindows.equals(parentID)) {
                driver.switchTo().window(runWindows);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    private By getByLocator(String locator) {
        if (locator == null || locator.isEmpty()) {
            throw new RuntimeException("Locator type is empty or null.");
        }

        return switch (locator.split("=")[0].toLowerCase()) {
            case "xpath" -> By.xpath(locator.substring(6));
            case "class" -> By.className(locator.substring(6));
            case "css" -> By.cssSelector(locator.substring(4));
            case "id" -> By.id(locator.substring(3));
            case "name" -> By.name(locator.substring(5));
            default -> throw new InvalidArgumentException("Locator type is not support");
        };
    }

    protected WebElement getWebElement(String locator) {
        return driver.findElement(getByLocator(locator));
    }

    protected List<WebElement> getListWebElement(String locator, String...restParam) {
        return driver.findElements(getByLocator(castLocator(locator, restParam)));
    }

    protected void clickToElement(String locator, String...restParam) {
        getWebElement(castLocator(locator, restParam)).click();
    }

    protected void sendKeyToElement(String locator, String value, String...restParam) {
        WebElement element = getWebElement(castLocator(locator, restParam));
        element.clear();
        element.sendKeys(value);
    }

    protected void selectItemDropdown(String locator, String textItem, String...restParam) {
        new Select(getWebElement(castLocator(locator, restParam))).selectByVisibleText(textItem);
    }

    protected String getSelectedItemInDropdown(String locator, String...restParam) {
        return new Select(getWebElement(castLocator(locator, restParam))).getFirstSelectedOption().getText();
    }

    protected boolean isDropdownMultiple(String locator) {
        return new Select(getWebElement(locator)).isMultiple();
    }

    protected void selectItemInCustomDropdown(String parentXpath, String childXpath, String textItem) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(parentXpath))).click();
        sleepInSecond(GlobalConstants.THREAD_TIMEOUT);

        List<WebElement> allItems = waitForAllElementPresence(childXpath);

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(textItem)) {
                item.click();
                break;
            }
        }
    }

    protected void selectItemInCustomDropdownByJS(String parentXpath, String childXpath, String expectedItems) {
        getWebElement(parentXpath).click();
        sleepInSecond(GlobalConstants.THREAD_TIMEOUT);

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));

        List<WebElement> allItemsElements = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childXpath)));
        for (WebElement item : allItemsElements) {
            if (item.getText().trim().equals(expectedItems)) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(GlobalConstants.THREAD_TIMEOUT);
                item.click();
                break;
            }
        }
    }

    public void sleepInSecond(long timeSecond) {
        try {
            Thread.sleep(timeSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getElementText(String locator, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).getText();
    }

    protected String getElementAttribute(String locator, String attributeName, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).getDomAttribute(attributeName);
    }

    protected String getCssValue(String locator, String propertyName, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).getCssValue(propertyName);
    }

    protected String getHexaByRgb(String rgb) {
        return Color.fromString(rgb).asHex().toUpperCase();
    }

    protected int getListElementSize(String locator, String...restParam) {
        return getListWebElement(castLocator(locator, restParam)).size();
    }

    protected void checkToCheckBox(String locator, String...restParam) {
        if (!getWebElement(castLocator(locator, restParam)).isSelected()) {
            getWebElement(castLocator(locator, restParam)).click();
        }
    }

    protected void uncheckToCheckBox(String locator, String...restParam) {
        if (getWebElement(castLocator(locator, restParam)).isSelected()) {
            getWebElement(castLocator(locator, restParam)).click();
        }
    }

    protected boolean isElementDisplayed(String locator, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).isDisplayed();
    }

    protected void overrideGlobalTimeout(long timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    protected boolean isElementUndisplayed(String locator) {
        overrideGlobalTimeout(GlobalConstants.SHORT_TIMEOUT);

        List<WebElement> elements = getListWebElement(locator);

        overrideGlobalTimeout(GlobalConstants.SHORT_TIMEOUT);

        if (elements.isEmpty()) {
            return true;
        } else if (!elements.getFirst().isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    public void uploadMultipleFiles(String... fileNames) {

        String filePath = GlobalConstants.UPLOAD_FILE;
        String fullFileName = "";

        for (String file : fileNames) {
            //fullFileName = fullFileName + filePath + file + "\n";
            fullFileName += filePath + file + "\n";
        }
        fullFileName = fullFileName.trim();
        getWebElement(GlobalConstants.UPLOAD_FILE_TYPE).sendKeys(fullFileName);
    }

    protected boolean isElementSelected(String locator, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).isSelected();
    }

    protected boolean isElementEnabled(String locator, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).isEnabled();
    }

    protected WebDriver switchToIframe(String locator, String...restParam) {
        return driver.switchTo().frame(getWebElement(castLocator(locator, restParam)));
    }

    protected WebDriver switchToDefaultContent() {
        return driver.switchTo().defaultContent();
    }

    protected void hoverToElement(String locator, String...restParam) {
        new Actions(driver).moveToElement(getWebElement(castLocator(locator, restParam))).perform();
    }

    protected void doubleClickToElement(String locator) {
        new Actions(driver).doubleClick(getWebElement(locator)).perform();
    }

    protected void rightClickToElement(String locator) {
        new Actions(driver).contextClick(getWebElement(locator)).perform();
    }

    protected void scrollToElement(String locator) {
        new Actions(driver).scrollToElement(getWebElement(locator)).perform();
    }

    protected void sendKeyBoardToElement(String locator, Keys keys, String...restParam) {
        new Actions(driver).sendKeys(getWebElement(castLocator(locator, restParam)), keys).perform();
    }

    protected String getDomain() {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.domain;");
    }

    protected String getInnerText() {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
    }

    protected void navigateToUrlByJS(String url) {
        ((JavascriptExecutor) driver).executeScript("window.location = '" + url + " ' ");
        sleepInSecond(GlobalConstants.THREAD_TIMEOUT);
    }

    protected void highlightElement(String locator) {
        WebElement element = getWebElement(locator);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(GlobalConstants.THREAD_TIMEOUT);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    protected void clickToElementByJS(String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(locator));
    }

    protected void scrollToElementOnTop(String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement(locator));
    }

    protected void scrollToElementToDown(String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", getWebElement(locator));
    }

    protected void setAttributeInDOM(String locator, String attributeName, String attributeValue) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(' " + attributeName + " ', ' " + attributeValue + " ');", getWebElement(locator));
    }

    protected void removeAttributeInDOM(String locator, String attributeName) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute(' " + attributeName + " ');", getWebElement(locator));
    }

    protected void sendKeyToElementByJS(String locator, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', ' " + value + " ')", getWebElement(locator));
    }

    protected String getAttributeInDOM(String locator, String attributeName) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].getAttribute(' " + attributeName + " ');", getWebElement(locator));
    }

    protected String getElementValidationErrorMessage(String locator) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", getWebElement(locator));
    }

    protected boolean isImageLoaded(String locator) {
        return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0"
                , getWebElement(locator));
    }

    protected boolean areJQueryAndJSLoadedSuccess() {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };
        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    protected boolean isPageLoadedSuccess() {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                // TODO Auto-generated method stub
                return (Boolean) jsExecutor.executeScript("return (window.jQuery!=null) && (jQuery.active===0);");
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                // TODO Auto-generated method stub
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };
        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    protected WebElement getShadowDOM(String locator, String...restParam) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = (WebElement) jsExecutor.executeAsyncScript("return arguments[0].shadowRoot", getWebElement(castLocator(locator, restParam)));
        return element;
    }

    protected WebElement waitForElementVisible(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(getByLocator(castLocator(locator, restParam))));
    }

    protected boolean waitForElementInvisible(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(castLocator(locator, restParam))));
    }

    protected List<WebElement> waitForAllElementVisible(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(castLocator(locator, restParam))));
    }

    protected boolean waitForAllElementInvisible(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(castLocator(locator, restParam))));
    }

    protected WebElement waitForElementClickable(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(getByLocator(castLocator(locator, restParam))));
    }

    protected WebElement waitForElementPresence(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(getByLocator(castLocator(locator, restParam))));
    }

    protected List<WebElement> waitForAllElementPresence(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(castLocator(locator, restParam))));
    }

    protected boolean waitForElementSelected(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.elementToBeSelected(getByLocator(castLocator(locator, restParam))));
    }

    protected void waitForElementUndisplayed(String locator, String...restParam) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.SHORT_TIMEOUT));
        overrideGlobalTimeout(GlobalConstants.SHORT_TIMEOUT);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(castLocator(locator, restParam))));
    }
}
