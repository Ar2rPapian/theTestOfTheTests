package am.testing.qe.util.driver;

import org.openqa.selenium.WebDriver;

public interface DriverFactory {

    WebDriver create();
    void initDriverDefaultCapabilitiesAndOptions();
    void setCapability(String capabilityName, String capabilityValue);
    String getCapability(String capabilityName);
    void resetDefaultCapabilitiesAndOptions();
}
