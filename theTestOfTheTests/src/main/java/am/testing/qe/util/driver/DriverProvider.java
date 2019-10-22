package am.testing.qe.util.driver;

import am.testing.qe.Initializer;
import am.testing.qe.util.Browser;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class DriverProvider {

    private static Map<Browser, WebDriver> drivers = new HashMap<>();

    private DriverProvider(){}

    public static WebDriver provideDriver() {
        Browser currentBrowser = Initializer.get().getCurrentBrowser();
        WebDriver driver = drivers.get(currentBrowser);
        if(driver != null){
            return driver;
        }
        WebDriver newDriver = provideDriverFor(currentBrowser);
        drivers.put(currentBrowser, newDriver);
        return newDriver;
    }

    public static WebDriver provideDriverFor(Browser browser) {
        switch (browser) {
            case CHROME:
                return new ChromeDriverFactory().create();
            case SAFARI:
                return new SafariDriverFactory().create();
            case FIREFOX:
                return new FirefoxDriverFactory().create();
            default:
                throw new RuntimeException("SOMETHING WENT WRONG");
        }
    }

}
