package am.testing.qe.util.testrailreporter.constants;


public class TestResult {

    private Integer caseId;
    private Integer status;
    private String comment;

    public Integer getCaseId() {
        return caseId;
    }

    public TestResult setCaseId(Integer testId) {
        this.caseId = testId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public TestResult setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public TestResult setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
