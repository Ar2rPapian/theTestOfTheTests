package am.testing.qe.util.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.logging.Level;

import static am.testing.qe.util.Browser.CHROME;

public class ChromeDriverFactory implements DriverFactory {

    private static boolean isDefault = true;

    private ChromeOptions chromeOptions = new ChromeOptions();

    private DesiredCapabilities capabilities = DesiredCapabilities.chrome();

    @Override
    public WebDriver create() {
        if (isDefault) {
            initDriverDefaultCapabilitiesAndOptions();
        }
        return new ChromeDriver(initDriver(), chromeOptions);
    }
    @Override
    public void initDriverDefaultCapabilitiesAndOptions()  {
        for (Options option : Options.values()) {
            chromeOptions.addArguments(option.getOption());
        }
        chromeOptions.setBinary(new File(CHROME.getPath()));
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        capabilities.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION, true);
        chromeOptions.merge(capabilities);
    }

    @Override
    public void resetDefaultCapabilitiesAndOptions() {
        chromeOptions = new ChromeOptions();
        capabilities = DesiredCapabilities.chrome();
    }

    @Override
    public void setCapability(String capabilityName, String capabilityValue) {
        isDefault = false;
        capabilities.setCapability(capabilityName, capabilityValue);
    }

    @Override
    public String getCapability(String capabilityName) {
        return capabilities.getCapability(capabilityName).toString();
    }

    public void setOption(Options option) {
        isDefault = false;
        chromeOptions.addArguments(option.getOption());
    }

    private ChromeDriverService initDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriverService.Builder().usingAnyFreePort().build();
    }

    public enum Options {
        // To ignore errors from chrome for using robot
        NOSANDBOX("--no-sandbox"),
        // To open browser in fullscreen
        STARTMAXIMIZED("--start-maximized"),
        // Disable ChromeDriverFactory info bars
        DISABLEINFOBARS("--disable-infobars"),
        // Disable ChromeDriverFactory notifications
        DISABLENOTIFICATIONS("--disable-notifications"),
        // Disable ChromeDriverFactory extensions
        DISABLEEXTENSIONS("--disable-extensions"),
        // Disable ChromeDriverFactory gpu
        DISABLEGPU("--disable-gpu"),
        // Enable ChromeDriverFactory automation
        ENABLEAUTOMATION("--enable-automation"),
        // Disable web security for automation
        DISABLEWEBSECURITY("--disable-web-security"),
        // Ignore certificate errors
        IGNORECERTIFICATEERRORS("--ignore-certificate-errors"),
        IGNOREURLFETCHER("--ignore-urlfetcher-cert-requests"),
        // Allow Running Insecure Content
        ALLOWRUNNINGINSECURECONTENT("--allow-running-insecure-content"),
        // No yellow security message would appear on screen
        TESTTYPE("test-type"),
        MAXIMIZEWINDOW("window-size=1920,1080"),
        FIX_NEW_TAB_SWITCH_HANGING("--disable-site-isolation-trials");

        private final String option;

        Options(String chromePath) {
            this.option = chromePath;
        }

        public String getOption() {
            return option;
        }
    }

}
