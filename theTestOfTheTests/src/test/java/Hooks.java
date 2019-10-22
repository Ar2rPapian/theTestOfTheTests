import am.testing.qe.util.annotations.TestCaseDescription;
import am.testing.qe.util.testrailreporter.TestRailReporter;
import am.testing.qe.util.testrailreporter.client.ClientException;
import am.testing.qe.util.testrailreporter.constants.TestResult;
import am.testing.qe.util.testrailreporter.constants.TestStatus;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;

public class Hooks {


    @Rule
    public final TestRule testRule = new TestWatcher() {

        private TestResult testResult = new TestResult();

        private TestCaseDescription testDescription;

        @Override
        protected void starting(Description description) {
            testDescription = description.getAnnotation(TestCaseDescription.class);
            testResult.setCaseId(testDescription.caseId());
        }

        @Override
        protected void failed(Throwable e, Description description) {
            String comment = String.format("CASE ID :: [ %s ] \n%s", testResult.getCaseId(), e.getMessage());
            testResult.setStatus(TestStatus.FAILED);
            testResult.setComment(comment);
        }

        @Override
        protected void succeeded(Description description) {
            testResult.setStatus(TestStatus.PASSED);
        }

        @Override
        protected void finished(Description description) {
            try {
                TestRailReporter.init("", 0, "", "");
                TestRailReporter.disable();
                TestRailReporter.addResultInTestRail(testResult.getCaseId(), testResult.getStatus(), testResult.getComment());
            } catch (IOException | ClientException e) {
                e.printStackTrace();
                System.out.println("Failed to send report to Testrail");
            }
        }
    };
}
