package am.testing.qe.factory.pages.admin.controlpanel;

import am.testing.qe.factory.pages.BasePage;
import am.testing.qe.util.annotations.Specifier;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class EditEntryPage extends BasePage<EditEntryPage> {


    private static final EditEntryPage EDIT_ENTRY_PAGE = new EditEntryPage();

    private EditEntryPage() {
    }

    public static EditEntryPage get() {
        return EDIT_ENTRY_PAGE;
    }

    @Specifier
    @FindBy(xpath = "//*[@id=\"entry_form\"]/div/div/p/a")
    private WebElement removeEntryButton;


    public DeletionConfirmationPage clickDeleteEntryButton() {
        removeEntryButton.click();
        return new DeletionConfirmationPage().init();
    }

    public static class DeletionConfirmationPage extends BasePage<DeletionConfirmationPage> {


        @Specifier
        @FindBy(xpath = "//*[@id=\"content\"]/form/div/input[2]")
        private WebElement yesDeleteButton;


        public AddEntryPage clickOnYesDeleteButton() {
            yesDeleteButton.click();
            return AddEntryPage.get().init();
        }
    }
}
