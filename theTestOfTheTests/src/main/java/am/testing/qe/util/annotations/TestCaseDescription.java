package am.testing.qe.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestCaseDescription {
    String name() default "";

    String runName() default "";

    String config() default "";

    String defects() default "";

    int caseId();
}
