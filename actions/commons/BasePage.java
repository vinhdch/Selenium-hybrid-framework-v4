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

//    public static BasePage getBasePage() {
//        return new BasePage();
//    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    private long longTimeout = GlobalConstants.LONG_TIMEOUT;
    private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
    private long threadTimeout = GlobalConstants.THREAD_TIMEOUT;

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
        sleepInSecond(longTimeout);
    }

    private Alert waitToAlertPresent() {
        return new WebDriverWait(driver, Duration.ofSeconds(longTimeout)).until(ExpectedConditions.alertIsPresent());
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

    private By getByXpath(String locator) {
        return By.xpath(locator);
    }

    public WebElement getWebElement(String locator) {
        return driver.findElement(getByXpath(locator));
    }

    public List<WebElement> getListWebElement(String locator) {
        return driver.findElements(getByXpath(locator));
    }

    public void clickToElement(String locator) {
        getWebElement(locator).click();
    }

    public void sendKeyToElement(String locator, String value) {
        getWebElement(locator).clear();
        getWebElement(locator).sendKeys(value);
    }

    public void selectItemDropdown(String locator, String textItem) {
        new Select(getWebElement(locator)).selectByVisibleText(textItem);
    }

    public String getSelectedItemInDropdown(String locator) {
        return new Select(getWebElement(locator)).getFirstSelectedOption().getText();
    }

    public boolean isDropdownMultiple(String locator) {
        return new Select(getWebElement(locator)).isMultiple();
    }

    public void selectItemInCustomDropdown(String parentXpath, String childXpath, String textItem) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(parentXpath))).click();
        sleepInSecond(threadTimeout);

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
        sleepInSecond(threadTimeout);

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));

        List<WebElement> allItemsElements = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));
        for (WebElement item : allItemsElements) {
            if (item.getText().trim().equals(expectedItems)) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(threadTimeout);
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

    public String getElementText(String locator) {
        return getWebElement(locator).getText();
    }

    public String getElementAttribute(String locator, String attributeName) {
        return getWebElement(locator).getAttribute(attributeName);
    }

    public String getCssValue(String locator, String propertyName) {
        return getWebElement(locator).getCssValue(propertyName);
    }

    public String getHexaByRgb(String rgb) {
        return Color.fromString(rgb).asHex().toUpperCase();
    }

    public int getListElementSize(String locator) {
        return getListWebElement(locator).size();
    }

    public void checkToCheckBox(String locator) {
        if (!getWebElement(locator).isSelected()) {
            getWebElement(locator).click();
        }
    }

    public void uncheckToCheckBox(String locator) {
        if (getWebElement(locator).isSelected()) {
            getWebElement(locator).click();
        }
    }

    public boolean isElementDisplayed(String locator) {
        return getWebElement(locator).isDisplayed();
    }

    public void overrideGlobalTimeout(long timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    public boolean isElementUndisplayed(String locator) {
        overrideGlobalTimeout(shortTimeout);

        List<WebElement> elements = getListWebElement(locator);

        overrideGlobalTimeout(shortTimeout);

        if (elements.size() == 0) {
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    public void uploadMulipleFiles(String... fileNames) {

        String filePath = GlobalConstants.UPLOAD_FILE;
        String fullFileName = "";

        for (String file : fileNames) {
            fullFileName = fullFileName + filePath + file + "\n";
        }
        fullFileName = fullFileName.trim();
        getWebElement(GlobalConstants.UPLOAD_FILE_JQUERY).sendKeys(fullFileName);
    }

    public boolean isElementSelected(String locator) {
        return getWebElement(locator).isSelected();
    }

    public boolean isElementEnabled(String locator) {
        return getWebElement(locator).isEnabled();
    }

    public WebDriver switchToIframe(String locator) {
        return driver.switchTo().frame(getWebElement(locator));
    }

    public WebDriver switchToDefaultContent() {
        return driver.switchTo().defaultContent();
    }

    public void hoverToElement(String locator) {
        new Actions(driver).moveToElement(getWebElement(locator)).perform();
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
        sleepInSecond(threadTimeout);
    }

    public void highlightElement(String locator) {
        WebElement element = getWebElement(locator);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(threadTimeout);
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
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
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
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
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

    protected WebElement getShahowDOM(String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = (WebElement) jsExecutor.executeAsyncScript("return arguments[0].shadowRoot", getWebElement(locatorType));
        return element;
    }

    public WebElement waitForElementVisible(String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(longTimeout)).until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
    }

    public boolean waitForElementInvisible(String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(longTimeout)).until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
    }

    public List<WebElement> waitForAllElementVisible(String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(longTimeout)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));
    }

    public boolean waitForAllElementInvisible(String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(longTimeout)).until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(locator)));
    }

    public WebElement waitForElementClickable(String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(longTimeout)).until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
    }

    public WebElement waitForElementPresence(String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(longTimeout)).until(ExpectedConditions.presenceOfElementLocated(getByXpath(locator)));
    }

    public List<WebElement> waitForAllElementPresence(String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(longTimeout)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(locator)));
    }

    public boolean waitForElementSelected(String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(longTimeout)).until(ExpectedConditions.elementToBeSelected(getByXpath(locator)));
    }

    public void waitForElementUndisplayed(String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(shortTimeout));
        overrideGlobalTimeout(shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locatorType)));
    }
}
