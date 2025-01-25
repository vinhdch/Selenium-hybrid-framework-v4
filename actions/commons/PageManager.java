package commons;

import org.openqa.selenium.WebDriver;
import pageObjects.nopcommerce.CustomerInfoPageObject;
import pageObjects.nopcommerce.HomePageObject;
import pageObjects.nopcommerce.LoginPageObject;
import pageObjects.nopcommerce.RegisterPageObject;

public class PageManager {

    public static Object getPage(WebDriver driver, String pageName) {
        switch (pageName) {
            case "HomePage": {
                return new HomePageObject(driver);
            }
            case "CustomerInfoPage": {
                return new CustomerInfoPageObject(driver);
            }
            case "LoginPage": {
                return new LoginPageObject(driver);
            }
            case "RegisterPage": {
                return new RegisterPageObject(driver);
            }
            default:
                return new IllegalArgumentException(pageName + "is Invalid");
        }

    }
}
