package am.testing.qe.factory.pages.blog;

import am.testing.qe.exceptions.ImpossibleContinueTestingException;
import am.testing.qe.factory.pages.BasePage;
import am.testing.qe.factory.pages.admin.controlpanel.EditEntryPage;
import am.testing.qe.support.Assertable;
import am.testing.qe.util.annotations.Specifier;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.function.Consumer;

public class BlogPage extends BasePage<BlogPage> {

    private static final BlogPage BLOG_PAGE = new BlogPage();

    private BlogPage() {
    }

    public static BlogPage get() {
        return BLOG_PAGE;
    }

    @Specifier
    @FindBy(id = "top_title")
    WebElement header;

    @FindBy(id = "entries")
    private
    List<WebElement> entriesList;

    public BlogPage openByUrl() {
        return BLOG_PAGE.open("blog");
    }

    public BlogPage withEntriesList(Consumer<Assertable<BlogPage>> action) {
        for (WebElement webElement : entriesList) {
            action.accept(assertableOf(webElement));
        }
        return this;
    }

    private List<WebElement> getEntriesList() {
        return entriesList;
    }

    public Assertable<BlogPage> lookAtEntryWithText(String text){
        WebElement needed = null;
        for (WebElement webElement : entriesList) {
            if (webElement.getText().equals(text)) {
                needed = webElement;
                break;
            }
        }
        if (needed == null) {
            throw new ImpossibleContinueTestingException("Needed entry does not exist");
        }
        return assertableOf(needed);
    }

    public EditEntryPage clickOnEntryItem(String text) {
        for (WebElement webElement : entriesList) {
            if (webElement.getText().equals(text)) {
                webElement.click();
                break;
            }
        }
        return EditEntryPage.get().init();
    }

    public SingleEntry lookAtFirstEntry() {
        return lookAtEntryAt(1);
    }

    public SingleEntry lookAtEntryAt(int position) {
        return new SingleEntry(position).init(false);
    }


    public class SingleEntry extends BasePage<SingleEntry> {


        @Specifier
        WebElement title;

        public SingleEntry(int position) {
            title = driver.findElement(By.xpath(String.format("//*[@id='entries']/h2[%d]/a", position)));
        }

        public Assertable<SingleEntry> lookAtTitle() {
            return assertableOf(title);
        }


        public BlogPage back() {
            return BLOG_PAGE;
        }
    }
}
