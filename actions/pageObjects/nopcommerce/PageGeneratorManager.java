package pageObjects.nopcommerce;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;

public class PageGeneratorManager {

    public static <T extends BasePage> T getPageInstance(Class<T> pageClass, WebDriver driver) {
        try {
            Constructor<T> constructor = pageClass.getConstructor(WebDriver.class);
            return constructor.newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Can not init pageClass" + pageClass.getSimpleName(), e);
        }
    }

    // only use for demo - can remove - using at Level_6_PageGenerator_II
    public static HomePageObject getHomePage(WebDriver driver) {
        return new HomePageObject(driver);
    }

//    public static LoginPageObject getLoginPage(WebDriver driver){
//        return new LoginPageObject(driver);
//    }
//
//    public static CustomerInfoPageObject getCustomerInfoPage(WebDriver driver){
//        return new CustomerInfoPageObject(driver);
//    }
//
//    public static RegisterPageObject getRegisterPage(WebDriver driver){
//        return new RegisterPageObject(driver);
//    }
}
