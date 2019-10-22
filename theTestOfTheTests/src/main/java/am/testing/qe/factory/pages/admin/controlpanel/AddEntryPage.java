package am.testing.qe.factory.pages.admin.controlpanel;

import am.testing.qe.factory.pages.BasePage;
import am.testing.qe.support.Assertable;
import am.testing.qe.util.annotations.Specifier;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddEntryPage extends BasePage<AddEntryPage> {

    private static final AddEntryPage ADD_ENTRY_PAGE = new AddEntryPage();

    private AddEntryPage() {
    }

    public static AddEntryPage get() {
        return ADD_ENTRY_PAGE;
    }

    @FindBy(xpath ="//*[@id=\"content\"]/h1")
    @Specifier
    private WebElement pageHeader;

    @FindBy(xpath ="//*[@id=\"id_title\"]")
    private WebElement entryTitle;

    @FindBy(xpath ="//*[@id=\"id_slug\"]")
    private WebElement entrySlug;

    @FindBy(xpath ="//*[@id=\"entry_form\"]/div/div/input[1]")
    private WebElement entrySaveButton;

    @FindBy(xpath ="//*[@id=\"id_text_markdown\"]")
    private WebElement entryTextMarkDown;

    @FindBy(xpath ="//*[@id=\"id_text\"]")
    private WebElement entryText;

    public AddEntryPage openByUrl() {
        return ADD_ENTRY_PAGE.open("admin/entry/");
    }

    public Assertable<AddEntryPage> lookAtPageHeader() {
        return assertableOf(pageHeader);
    }

    public AddEntryPage fillEntryTitle(String title) {
        entryTitle.sendKeys(title);
        return this;
    }

    public AddEntryPage fillEntrySlug(String slug) {
        entrySlug.sendKeys(slug);
        return this;
    }

    public AddEntryPage fillEntryTextMarkDown(String text) {
        entryTextMarkDown.sendKeys(text);
        return this;
    }

    public AddEntryPage fillEntryText(String text) {
        entryText.sendKeys(text);
        return this;
    }

    public AdminBlogEntriesPage clickOnSaveButton() {
        entrySaveButton.click();
        return AdminBlogEntriesPage.get().init();
    }
}
