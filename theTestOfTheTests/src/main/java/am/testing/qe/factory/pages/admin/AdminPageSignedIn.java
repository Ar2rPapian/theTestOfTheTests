package am.testing.qe.factory.pages.admin;

import am.testing.qe.factory.pages.BasePage;
import am.testing.qe.factory.pages.admin.controlpanel.AddEntryPage;
import am.testing.qe.support.Assertable;
import am.testing.qe.util.annotations.Specifier;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPageSignedIn extends BasePage<AdminPageSignedIn> {

    private static final AdminPageSignedIn ADMIN_PAGE_SIGNED_IN = new AdminPageSignedIn();

    private AdminPageSignedIn() {
    }

    public static AdminPageSignedIn get() {
        return ADMIN_PAGE_SIGNED_IN;
    }

    @Specifier
    @FindBy(xpath = "//*[@id='navigation-menu']/li[1]/a")
    private WebElement controlPanel;

    @FindBy(xpath = "//*[@href='/admin/blog/entry/add/']")
    private static WebElement addEntryButtonBlogSection;

    public Assertable<AdminPageSignedIn> lookAtControlPanel() {
        return assertableOf(controlPanel);
    }

    public AddEntryPage clickOnTheAddEntryButtonOnTheBlogSection() {
        addEntryButtonBlogSection.click();
        return AddEntryPage.get().init();
    }

    @Override
    public AdminPageSignedIn openByUrl() {
        return ADMIN_PAGE_SIGNED_IN.open("admin/");
    }
}
