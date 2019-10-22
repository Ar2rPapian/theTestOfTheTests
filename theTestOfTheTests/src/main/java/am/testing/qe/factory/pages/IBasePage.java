package am.testing.qe.factory.pages;

import am.testing.qe.exceptions.ImpossibleContinueTestingException;
import am.testing.qe.support.Assertable;
import org.openqa.selenium.WebElement;

import static java.lang.String.format;

public interface IBasePage<Main extends IBasePage<Main>> {

    default Main openByUrl() {
        throw new ImpossibleContinueTestingException(format("No implemented for %s page", getPage().getClass().getSimpleName()));
    }

    Main getPage();

    Main init();

    default Assertable<Main> assertableOf(WebElement element) {
        return new Assertable<>(getPage(), element);
    }

}
