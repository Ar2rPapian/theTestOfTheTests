package am.testing.qe.util.testrailreporter.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {

    public int id;
    @JsonProperty("case_id")
    public int caseId;
    @JsonProperty("statusId")
    public int status_id;
    @JsonProperty("run_id")
    public int runId;
}
