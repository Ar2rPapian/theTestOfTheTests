package am.testing.qe.support;

import am.testing.qe.factory.pages.IBasePage;
import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class Assertable<ElementOwner extends IBasePage<ElementOwner>> {

    //fluentizeing :D
    private ElementOwner page;

    private WebElement element;

    public Assertable(ElementOwner page, WebElement element) {
        this.page = page;
        this.element = element;
    }

    public Assertion assertThat(){
        return new Assertion(element);
    }

    public class Assertion extends AbstractAssert<Assertion, WebElement> {

        public Assertion(WebElement webElement) {
            super(webElement, Assertion.class);
        }

        public Assertion isDisplayed(){
            isNotNull();
            //check condition
            if(!actual.isDisplayed()){
                failWithMessage("Expected element to be displayed. But was not!!");
            }

            return this;
        }

        public Assertion isEnabled(){
            isNotNull();

            //check condition
            if(!actual.isEnabled()){
                failWithMessage("Expected element to be enabled. But was not!!");
            }

            return this;
        }

        public Assertion withText(String text){
            isNotNull();

            //check condition

            String actualText = actual.getText();

            if(!actualText.equals(text)){
                failWithMessage("Expected "+text+". But was " + actualText);
            }

            return this;
        }

        public Assertion containsElementWithText(String text){
            isNotNull();

            try {
                actual.findElement(By.linkText(text));
            } catch (NoSuchElementException e) {
                failWithMessage("Expected element to have descendant with text <%s> But was not!!", text);
            }

            return this;
        }


        public Assertion hasAttributeValue(String attr, String value){
            isNotNull();

            //check condition
            if(!actual.getAttribute(attr).equals(value)){
                failWithMessage("Expected element to have attr <%s> value as <%s>. But was not!!", attr, value);
            }

            return this;
        }


        public ElementOwner done() {
            return page;
        }
    }
}

