package am.testing.qe.exceptions;

public class PageIsNotOpenedException extends ImpossibleContinueTestingException {
    public PageIsNotOpenedException(String why) {
        super(why);
    }
}
