package am.testing.qe.support;

import am.testing.qe.exceptions.ImpossibleContinueTestingException;

public class Wait {

    public interface Condition {
        boolean isTrue();
    }

    public static void until(Condition condition, int timeout, Runnable onProcess, boolean soft) {
        if (condition == null) throw new RuntimeException("Null condition");
        long startTime = System.currentTimeMillis();
        do {
            if (onProcess != null) onProcess.run();
        } while (System.currentTimeMillis() - startTime < timeout && !condition.isTrue());
        if (!soft && !condition.isTrue()) {
            throw new ImpossibleContinueTestingException("Condition is not true");
        }
    }

    public static void until(Condition condition, int timeout, Runnable onProcess) {
        until(condition, timeout, onProcess, true);
    }
}
