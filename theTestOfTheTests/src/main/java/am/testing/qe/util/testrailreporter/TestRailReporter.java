package am.testing.qe.util.testrailreporter;

import am.testing.qe.util.testrailreporter.client.ClientException;
import am.testing.qe.util.testrailreporter.client.TestRailClient;
import am.testing.qe.util.testrailreporter.objects.Run;

import java.io.IOException;


public class TestRailReporter {

    private static boolean enabled;

    private static String url;
    private static Integer projectId;
    private static String username;
    private static String password;

    public static void init(String url, Integer projectId, String username, String password) {
        TestRailReporter.url = url;
        TestRailReporter.projectId = projectId;
        TestRailReporter.username = username;
        TestRailReporter.password = password;
    }


    public static void addResultInTestRail(int caseId, int statusId, String comment) throws IOException, ClientException {
        start(caseId, statusId, comment);
    }

    private static void start(int caseId, int statusId, String comment) throws IOException, ClientException {
        if (enabled)
            if ((url != null) && (username != null) && (password != null) && (projectId != null)) {
                TestRailClient client = new TestRailClient(url, username, password);
                Run run = client.createRunOrUpdateIfExistForToday(projectId, caseId);
                client.addResultForCase(run.id, caseId, statusId, comment);
            } else System.out.println("Incorrect configurations");

        else System.out.println("Reporter disabled.");
    }

    public static void enable() {
        TestRailReporter.enabled = true;
    }

    public static void disable() {
        TestRailReporter.enabled = false;
    }
}
