package commons;

import org.apache.commons.exec.util.StringUtils;
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

    public void openPageUrl(String pageUrl) {
        driver.get(pageUrl);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public void backToPage() {
        driver.navigate().back();
    }

    public void forwardToPage() {
        driver.navigate().forward();
    }

    public void refreshCurrentPage() {
        driver.navigate().refresh();
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    public void setCookies(Set<Cookie> cookies) {

        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
        sleepInSecond(GlobalConstants.LONG_TIMEOUT);
    }

    private Alert waitToAlertPresent() {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert() {
        waitToAlertPresent().accept();
    }

    public void cancelAlert() {
        waitToAlertPresent().dismiss();
    }

    public void sendKeyToAlert(String value) {
        waitToAlertPresent().sendKeys(value);
    }

    public String getAlertText() {
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

    public void switchToWindowByTitle(String title) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            driver.switchTo().window(runWindows);
            String currentWin = driver.getTitle();
            if (currentWin.equals(title)) {
                break;
            }
        }
    }

    public void closeAllWindowsWithoutParent(String parentID) {
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
            case "xpath", "class" -> By.xpath(locator.substring(6));
            case "css" -> By.xpath(locator.substring(4));
            case "id" -> By.xpath(locator.substring(3));
            case "name" -> By.xpath(locator.substring(5));
            default -> throw new InvalidArgumentException("Locator type is not support");
        };
    }

    public WebElement getWebElement(String locator) {
        return driver.findElement(getByLocator(locator));
    }

    public List<WebElement> getListWebElement(String locator) {
        return driver.findElements(getByLocator(locator));
    }

    public void clickToElement(String locator, String...restParam) {
        getWebElement(castLocator(locator, restParam)).click();
    }

    public void sendKeyToElement(String locator, String value, String...restParam) {
        WebElement element = getWebElement(castLocator(locator, restParam));
        element.clear();
        element.sendKeys(value);
    }

    public void selectItemDropdown(String locator, String textItem, String...restParam) {
        new Select(getWebElement(castLocator(locator, restParam))).selectByVisibleText(textItem);
    }

    public String getSelectedItemInDropdown(String locator, String...restParam) {
        return new Select(getWebElement(castLocator(locator, restParam))).getFirstSelectedOption().getText();
    }

    public boolean isDropdownMultiple(String locator) {
        return new Select(getWebElement(locator)).isMultiple();
    }

    public void selectItemInCustomDropdown(String parentXpath, String childXpath, String textItem) {
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

    public String getElementText(String locator, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).getText();
    }

    public String getElementAttribute(String locator, String attributeName, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).getDomAttribute(attributeName);
    }

    public String getCssValue(String locator, String propertyName, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).getCssValue(propertyName);
    }

    public String getHexaByRgb(String rgb) {
        return Color.fromString(rgb).asHex().toUpperCase();
    }

    public int getListElementSize(String locator, String...restParam) {
        return getListWebElement(castLocator(locator, restParam)).size();
    }

    public void checkToCheckBox(String locator, String...restParam) {
        if (!getWebElement(castLocator(locator, restParam)).isSelected()) {
            getWebElement(castLocator(locator, restParam)).click();
        }
    }

    public void uncheckToCheckBox(String locator, String...restParam) {
        if (getWebElement(castLocator(locator, restParam)).isSelected()) {
            getWebElement(castLocator(locator, restParam)).click();
        }
    }

    public boolean isElementDisplayed(String locator) {
        return getWebElement(locator).isDisplayed();
    }

    public void overrideGlobalTimeout(long timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    public boolean isElementUndisplayed(String locator) {
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
            fullFileName = fullFileName + filePath + file + "\n";
        }
        fullFileName = fullFileName.trim();
        getWebElement(GlobalConstants.UPLOAD_FILE_JQUERY).sendKeys(fullFileName);
    }

    public boolean isElementSelected(String locator, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).isSelected();
    }

    public boolean isElementEnabled(String locator, String...restParam) {
        return getWebElement(castLocator(locator, restParam)).isEnabled();
    }

    public WebDriver switchToIframe(String locator, String...restParam) {
        return driver.switchTo().frame(getWebElement(castLocator(locator, restParam)));
    }

    public WebDriver switchToDefaultContent() {
        return driver.switchTo().defaultContent();
    }

    public void hoverToElement(String locator, String...restParam) {
        new Actions(driver).moveToElement(getWebElement(castLocator(locator, restParam))).perform();
    }

    public void doubleClickToElement(String locator) {
        new Actions(driver).doubleClick(getWebElement(locator)).perform();
    }

    public void rightClickToElement(String locator) {
        new Actions(driver).contextClick(getWebElement(locator)).perform();
    }

    public void scrollToElement(String locator) {
        new Actions(driver).scrollToElement(getWebElement(locator)).perform();
    }

    public void sendKeyBoardToElement(String locator, Keys keys) {
        new Actions(driver).sendKeys(getWebElement(locator), keys).perform();
    }

    public String getDomain() {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.domain;");
    }

    public String getInnerText() {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
    }

    public void navigateToUrlByJS(String url) {
        ((JavascriptExecutor) driver).executeScript("window.location = '" + url + " ' ");
        sleepInSecond(GlobalConstants.THREAD_TIMEOUT);
    }

    public void highlightElement(String locator) {
        WebElement element = getWebElement(locator);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(GlobalConstants.THREAD_TIMEOUT);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void clickToElementByJS(String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(locator));
    }

    public void scrollToElementOnTop(String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement(locator));
    }

    public void scrollToElementToDown(String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", getWebElement(locator));
    }

    public void setAttributeInDOM(String locator, String attributeName, String attributeValue) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(' " + attributeName + " ', ' " + attributeValue + " ');", getWebElement(locator));
    }

    public void removeAttributeInDOM(String locator, String attributeName) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute(' " + attributeName + " ');", getWebElement(locator));
    }

    public void sendKeyToElementByJS(String locator, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', ' " + value + " ')", getWebElement(locator));
    }

    public String getAttributeInDOM(String locator, String attributeName) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].getAttribute(' " + attributeName + " ');", getWebElement(locator));
    }

    public String getElementValidationErrorMessage(String locator) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", getWebElement(locator));
    }

    public boolean isImageLoaded(String locator) {
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

    public WebElement waitForElementVisible(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(getByLocator(castLocator(locator, restParam))));
    }

    public boolean waitForElementInvisible(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(castLocator(locator, restParam))));
    }

    public List<WebElement> waitForAllElementVisible(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(castLocator(locator, restParam))));
    }

    public boolean waitForAllElementInvisible(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(castLocator(locator, restParam))));
    }

    public WebElement waitForElementClickable(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(getByLocator(castLocator(locator, restParam))));
    }

    public WebElement waitForElementPresence(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(getByLocator(castLocator(locator, restParam))));
    }

    public List<WebElement> waitForAllElementPresence(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(castLocator(locator, restParam))));
    }

    public boolean waitForElementSelected(String locator, String...restParam) {
        return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions.elementToBeSelected(getByLocator(castLocator(locator, restParam))));
    }

    public void waitForElementUndisplayed(String locator, String...restParam) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.SHORT_TIMEOUT));
        overrideGlobalTimeout(GlobalConstants.SHORT_TIMEOUT);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(castLocator(locator, restParam))));
    }
}
