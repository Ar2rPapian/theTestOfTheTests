package am.testing.qe.util.driver;

import am.testing.qe.exceptions.ImpossibleContinueTestingException;
import am.testing.qe.util.Browser;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.logging.Level;

import static am.testing.qe.util.Browser.CHROME;
import static am.testing.qe.util.Browser.FIREFOX;

public class FirefoxDriverFactory implements DriverFactory {

    private static boolean isDefault;

    private FirefoxOptions firefoxOptions;
    private DesiredCapabilities capabilities;

    public FirefoxDriverFactory() {
        isDefault = true;
        firefoxOptions = new FirefoxOptions();
        capabilities = DesiredCapabilities.firefox();
    }

    @Override
    public WebDriver create() {
        if (isDefault) {
            initDriverDefaultCapabilitiesAndOptions();
        }
        return new FirefoxDriver(firefoxOptions);
    }

    public void setFirefoxProfile () {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
    }

    @Override
    public void initDriverDefaultCapabilitiesAndOptions()  {
        this.setFirefoxProfile();
        for (Options option : Options.values()) {
            firefoxOptions.addArguments(option.getOption());
        }
        String path = FIREFOX.getPath();
        firefoxOptions.setBinary(path);
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        capabilities.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION, true);
        firefoxOptions.merge(capabilities);
    }

    @Override
    public void resetDefaultCapabilitiesAndOptions() {
        firefoxOptions = new FirefoxOptions();
        capabilities = DesiredCapabilities.firefox();
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


    private String getBrowserPath() {
        return Browser.FIREFOX.getPath();
    }


    public enum Options {
        SAVE_MODE("--safe-mode");

        private final String option;

        Options(String chromePath) {
            this.option = chromePath;
        }

        public String getOption() {
            return option;
        }
    }
}
