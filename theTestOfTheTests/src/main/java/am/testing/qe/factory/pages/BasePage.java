package am.testing.qe.factory.pages;

import am.testing.qe.exceptions.PageIsNotOpenedException;
import am.testing.qe.support.Wait;
import am.testing.qe.util.annotations.Specifier;
import am.testing.qe.util.driver.DriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static am.testing.qe.config.Constants.DEFAULT_TIMEOUT_4_EVERYTHING;
import static am.testing.qe.support.Ui.elementIsDisplaying;


public class BasePage<Main extends BasePage<Main>> implements IBasePage<Main> {

    protected WebDriver driver;

    protected BasePage(){
        driver = DriverProvider.provideDriver();
    }

    protected final String BASE_URL = "http://igorakintev.ru/";


    protected Main open(String path){
        driver.get(BASE_URL + path);
        return init(true);
    }

    public Main init(boolean hasProxyElements) {
        return init(null, hasProxyElements);
    }

    public Main init(Runnable onInit) {
        return init(onInit, true);
    }

    public Main init() {
        return init(null, true);
    }

    public Main init(Runnable onInit, boolean hasProxyElements) {
        if (hasProxyElements) {
            PageFactory.initElements(driver, getPage());
        }
        getSpecifiers().forEach((e) -> {
            waitForSpecifier(e, DEFAULT_TIMEOUT_4_EVERYTHING, onInit);
        });
        getSpecifiers().forEach((e) -> {
            if (!elementIsDisplaying(e)) {
                throw new PageIsNotOpenedException(String.format("The %s page is not opened because of the %s specifier is not displayed on the screen", getPage().getClass().getSimpleName(), e.toString()));
            }
        });
        return getPage();
    }

    private void waitForSpecifier(WebElement e, int timeout, Runnable onWait) {
        Wait.until(() -> elementIsDisplaying(e), timeout, onWait);
    }

    private List<WebElement> getSpecifiers() {
        Stream<Field> fieldStream = Arrays.stream(getPage().getClass().getDeclaredFields()).filter((e) -> e.isAnnotationPresent(Specifier.class));
        List<WebElement> specifiers = new ArrayList<>();
        fieldStream.forEach((e) -> {
            try {
                e.setAccessible(true);
                specifiers.add((WebElement) e.get(getPage()));
            } catch (IllegalAccessException ex) {
                throw new RuntimeException("Could not get specifier of: " + getPage().getClass().getSimpleName());
            }
        });
        return specifiers;
    }

    public Main closeBrowser(){
        driver.close();
        driver.quit();
        return getPage();
    }

    public Main closeCurrentTab(){
        driver.close();
        return getPage();
    }


    // NEVER GONNA THROW ANY EXCEPTION BECAUSE OF RECURSIVE GENERICS
    @SuppressWarnings("unchecked")
    @Override
    public Main getPage(){
        return ((Main) this);
    }

    public Main doNothingDuringThe(int timeout){
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getPage();
    }

    public String getPageTitle(){return driver.getTitle();}

    public String getPageURL(){return driver.getCurrentUrl();}


}
