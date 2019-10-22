package am.testing.qe.support;

import org.openqa.selenium.WebElement;

public class Ui {


    public static boolean elementIsDisplaying(WebElement e) {
        try {
            e.isDisplayed();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }


}
