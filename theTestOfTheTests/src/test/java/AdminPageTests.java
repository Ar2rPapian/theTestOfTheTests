import am.testing.qe.factory.pages.admin.AdminPageSignedIn;
import am.testing.qe.factory.pages.admin.AdminPageSignedOut;
import am.testing.qe.factory.pages.admin.controlpanel.AdminBlogEntriesPage;
import am.testing.qe.factory.pages.blog.BlogPage;
import am.testing.qe.util.Entry;
import am.testing.qe.util.annotations.TestCaseDescription;
import org.junit.*;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;  // main one

public class AdminPageTests extends Hooks {

    private static AdminPageSignedIn adminPageSignedIn;

    private static List<Entry> createdEntries = new ArrayList<>();

    @BeforeClass
    public static void beforeAll() {
        adminPageSignedIn = AdminPageSignedOut
                .get()
                .openByUrl()
                .signInWithDefaultCredentials();
    }


    @AfterClass
    public static void afterAll() {
        AdminBlogEntriesPage adminBlogEntriesPage = AdminBlogEntriesPage
                .get().openByUrl();
        createdEntries.forEach(
                (entry) -> adminBlogEntriesPage
                        .clickOnEntryWithTitle(entry.title)
                        .clickDeleteEntryButton()
                        .clickOnYesDeleteButton()
        );

        adminBlogEntriesPage.closeBrowser();
    }

    // TZ TEST

    @Test
    @TestCaseDescription(caseId = 1337)
    public void verifyEntryAddingFunctionality() {
        Entry entry = Entry.provideRandomEntry("Created_by_automation ");
        adminPageSignedIn
                .lookAtControlPanel().assertThat().isDisplayed().withText("ПАНЕЛЬ УПРАВЛЕНИЯ").done()
                .clickOnTheAddEntryButtonOnTheBlogSection()
                .lookAtPageHeader().assertThat().isDisplayed().withText("Добавить entry").done()
                .fillEntryTitle(entry.title)
                .fillEntrySlug(entry.slug)
                .fillEntryTextMarkDown(entry.markdownText)
                .fillEntryText(entry.text)
                .clickOnSaveButton();
        BlogPage.get().openByUrl()
                .lookAtFirstEntry()
                .lookAtTitle().assertThat().isDisplayed().withText(entry.title).done();
        createdEntries.add(entry);
    }

}

