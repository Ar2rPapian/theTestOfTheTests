package am.testing.qe.exceptions;

public class ImpossibleContinueTestingException extends RuntimeException {
    public ImpossibleContinueTestingException(String why) {
        super(why);
    }
}
