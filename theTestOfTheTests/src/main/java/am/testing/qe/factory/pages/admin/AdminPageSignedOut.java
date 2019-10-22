package am.testing.qe.factory.pages.admin;

import am.testing.qe.exceptions.ImpossibleContinueTestingException;
import am.testing.qe.factory.pages.BasePage;
import am.testing.qe.util.annotations.Specifier;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static am.testing.qe.support.Ui.elementIsDisplaying;

public class AdminPageSignedOut extends BasePage<AdminPageSignedOut> {

    private static final AdminPageSignedOut ADMIN_PAGE_SIGNED_OUT = new AdminPageSignedOut();

    private AdminPageSignedOut() {
    }

    public static AdminPageSignedOut get() {
        return ADMIN_PAGE_SIGNED_OUT;
    }

    @Specifier
    @FindBy(id = "id_username")
    private WebElement emailField;

    @Specifier
    @FindBy(id = "id_password")
    private WebElement passwordField;

    @Specifier
    @FindBy(xpath = "//*[@type=\"submit\"]")
    private WebElement loginButton;

    @FindBy(className = "errornote")
    private WebElement errorMessage;

    public final AdminPageSignedOut openByUrl() {
        return ADMIN_PAGE_SIGNED_OUT.open("admin/");
    }

    public AdminPageSignedOut fillEmailField(String email) {
        emailField.sendKeys(email);
        return this;
    }

    public AdminPageSignedOut fillPasswordField(String super_password) {
        passwordField.sendKeys(super_password);
        return this;
    }

    public SignInResult clickOnLoginButton() {
        loginButton.click();
        AdminPageSignedIn adminPageSignedIn;
        try {
            adminPageSignedIn = AdminPageSignedIn.get().init(() -> {
                if (elementIsDisplaying(errorMessage)) {
                    throw new ImpossibleContinueTestingException("");
                }
            });
            return new SignInResult.Success(adminPageSignedIn);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
            return new SignInResult.Failure(this);
        }
    }

    public AdminPageSignedIn signInWithDefaultCredentials() {
        return fillEmailField("selenium")
                .fillPasswordField("super_password")
                .clickOnLoginButton()
                .shouldBeSuccessful();
    }
}
