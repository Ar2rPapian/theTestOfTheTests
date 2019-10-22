package am.testing.qe.util.driver;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.logging.Level;

public class SafariDriverFactory implements DriverFactory {

    private static boolean isDefault;

    private SafariOptions safariOptions;

    private DesiredCapabilities capabilities;

    public SafariDriverFactory() {
        isDefault = true;
        //capabilities = DesiredCapabilities.safari();
    }

    @Override
    public WebDriver create()  {
        if (isDefault) {
            initDriverDefaultCapabilitiesAndOptions();
        }
        return new SafariDriver();
    }

    @Override
    public void initDriverDefaultCapabilitiesAndOptions() {
        resetDefaultCapabilitiesAndOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        capabilities.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION, true);
        safariOptions.merge(capabilities);
    }

    @Override
    public void setCapability(String capabilityName, String capabilityValue) {
        capabilities.setCapability(capabilityName, capabilityValue);
    }

    @Override
    public String getCapability(String capabilityName) {
        return capabilities.getCapability(capabilityName).toString();
    }

    @Override
    public void resetDefaultCapabilitiesAndOptions() {
        safariOptions = new SafariOptions();
        capabilities = DesiredCapabilities.safari();
    }

}
