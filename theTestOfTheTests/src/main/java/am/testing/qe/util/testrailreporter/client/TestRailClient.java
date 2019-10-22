package am.testing.qe.util.testrailreporter.client;

import am.testing.qe.util.testrailreporter.constants.TestRailConstants;
import am.testing.qe.util.testrailreporter.objects.Result;

import am.testing.qe.util.testrailreporter.objects.Run;
import am.testing.qe.util.testrailreporter.objects.Test;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class TestRailClient {

    private APIClient client;
    private ObjectMapper objectMapper;

    public TestRailClient(String url, String username, String password) {
        client = new APIClient(url, username, password);
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public Result addResultForCase(int runId, int caseId, int statusId, String comment)
            throws IOException, ClientException {
        String url = "add_result_for_case/" + runId + "/" + caseId;
        Map<String, Object> body = new HashMap<>();
        body.put("status_id", statusId);
        if (comment != null && !comment.equals("")) {
            body.put("comment", comment);
        }
        return objectMapper.readValue(client.invokeHttpPost(url, objectMapper.writeValueAsString(body)), Result.class);
    }

    public List<Test> getTests(int runId) throws IOException, ClientException {
        return objectMapper.readValue(client.invokeHttpGet("get_tests/" + runId), new TypeReference<List<Test>>() {
        });
    }

    public Run updateRun(Integer caseId, int runId) throws IOException, ClientException {
        List<Test> tests = getTests(runId);
        List<Integer> cases = new ArrayList<>(caseId);
        for (Test test : tests) {
            cases.add(test.caseId);
        }
        Map<String, Object> body = new HashMap<>();
        body.put("include_all", false);
        body.put("case_ids", cases);
        return objectMapper.readValue(
                client.invokeHttpPost("update_run/" + runId, objectMapper.writeValueAsString(body)),
                Run.class);
    }

    public Run addRun(int projectId, Integer caseId, String runName) throws IOException, ClientException {
        Map<String, Object> body = new HashMap<>();
        body.put("include_all", false);
        body.put("description", "CREATED BY ANDROID AUTOMATION FRAMEWORK ");
        body.put("name", runName);
        body.put("case_ids", new int[]{caseId});
        return objectMapper.readValue(client.invokeHttpPost("add_run/" + projectId, objectMapper.writeValueAsString(body)), Run.class);
    }

    public List<Run> getRuns(int projectId) throws IOException, ClientException {
        return objectMapper.readValue(client.invokeHttpGet("get_runs/" + projectId + "&limit=25"), new TypeReference<List<Run>>() {
        });
    }

    public Run getRun(int runId) throws IOException, ClientException {
        return objectMapper.readValue(client.invokeHttpGet("get_run/" + runId), Run.class);
    }

    public Run createRunOrUpdateIfExistForToday(int projectId, Integer caseId) throws IOException, ClientException {
        List<Run> runs = getRuns(projectId);
        Run run;
        Run currentRun = findRunWithName(runs, TestRailConstants.TESTRAIL_RUN_NAME);
        if (currentRun == null) {
            run = addRun(projectId, caseId, TestRailConstants.TESTRAIL_RUN_NAME);
        } else {
            run = updateRun(caseId, currentRun.id);
        }
        return run;
    }

    private Run findRunWithName(List<Run> runs, String runName) {
        boolean runOfTodayFound;
        for (Run run : runs) {
            runOfTodayFound = run.name != null && run.name.equals(runName);
            if (runOfTodayFound) {
                return run;
            }
        }
        return null;
    }
}



