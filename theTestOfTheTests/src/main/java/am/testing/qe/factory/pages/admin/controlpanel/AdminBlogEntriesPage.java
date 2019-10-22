package am.testing.qe.factory.pages.admin.controlpanel;

import am.testing.qe.factory.pages.BasePage;
import am.testing.qe.util.annotations.Specifier;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminBlogEntriesPage extends BasePage<AdminBlogEntriesPage> {

    private static final AdminBlogEntriesPage ADMIN_BLOG_ENTRIES_PAGE = new AdminBlogEntriesPage();

    private AdminBlogEntriesPage() {
    }

    public static AdminBlogEntriesPage get() {
        return ADMIN_BLOG_ENTRIES_PAGE;
    }

    @Specifier
    @FindBy(xpath = "//*[@id=\"content\"]/h1")
    WebElement pageHeader;

    public WebElement getEntryAt(int position) {
        return driver.findElement(By.cssSelector(String.format("#result_list > tbody > tr:nth-child(%d)", position)));
    }

    @Override
    public AdminBlogEntriesPage openByUrl() {
        return super.open("admin/blog/entry/");
    }


    public EditEntryPage clickOnEntryAt(int position) {
        getEntryAt(position).click();
        return EditEntryPage.get().init();
    }


    public EditEntryPage clickOnEntryWithTitle(String title) {
        driver.findElement(By.linkText(title)).click();
        return EditEntryPage.get().init();
    }
}
