import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v125.network.Network;
import org.openqa.selenium.devtools.v125.network.model.Headers;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Authentication_Alert {
    WebDriver driver;
    @Test
   public void authen_alert() {
       driver = new ChromeDriver();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
       // get dev tools object
       DevTools devTools = ((HasDevTools) driver).getDevTools();

       // start new session
       devTools.createSession();

       // enable the network domain of devtools
       devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

       // encode username/password
       Map<String, Object> headers = new HashMap<String, Object>();
       String basicAuthen = "Basic" + new String(new Base64().encode(String.format("%s:%s", "admin", "admin").getBytes()));
       headers.put("Authorization", basicAuthen);

       // set to header
       // header lien quan den version cua chrome - devtoolsv125
       devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

       driver.get("https://the-internet.herokuapp.com/basic_auth");

        driver.quit();
   }
}
