package commons;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.Random;

public class BaseTest {
    WebDriver driver;

    protected WebDriver getBrowserDriverName(String url, String browser) {
        BrowserType browserType = BrowserType.valueOf(browser.toUpperCase());
        switch (browserType) {
            case CHROME: {
                driver = new ChromeDriver();
                break;
            }
            case EDGE: {
                driver = new EdgeDriver();
                break;
            }
            case FIREFOX: {
                driver = new FirefoxDriver();
                break;
            }
            default: {
                throw new IllegalArgumentException("BrowserName is invalid");
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();

        driver.get(url);

        return driver;
    }

    protected void quitBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected int generateFakeNumber() {
        Random ran = new Random();
        return ran.nextInt(99999);
    }
}
