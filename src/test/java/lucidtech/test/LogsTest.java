package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class LogsTest extends ClientTest {
    private void assertLog(JSONObject log) throws IOException {
        Assert.assertTrue(log.has("events"));
        Assert.assertTrue(log.has("logId"));
        Assert.assertTrue(log.has("startTime"));
        Assert.assertTrue(log.has("transitionExecutionId"));
        Assert.assertTrue(log.has("transitionId"));
        Assert.assertTrue(log.has("workflowExecutionId"));
        Assert.assertTrue(log.has("workflowId"));
    }

    private void assertLogs(JSONObject logs) throws IOException {
        Assert.assertTrue(logs.has("logs"));
        Assert.assertTrue(logs.has("nextToken"));

        for (Object log: logs.getJSONArray("logs")) {
            this.assertLog((JSONObject) log);
        }
    }

    @Test
    public void testGetLog() throws IOException, APIException, MissingAccessTokenException {
        JSONObject log = this.client.getLog(TestUtils.logId());
        this.assertLog(log);
    }

    @Test
    public void testListLogs() throws IOException, APIException, MissingAccessTokenException {
        JSONObject logs = this.client.listLogs();
        this.assertLogs(logs);
    }

    @Test
    public void testListLogsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListLogsOptions options = new ListLogsOptions()
            .setTransitionId(TestUtils.transitionId())
            .setTransitionExecutionId(TestUtils.transitionExecutionId())
            .setWorkflowId(TestUtils.workflowId())
            .setWorkflowExecutionId(TestUtils.workflowExecutionId())
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject logs = this.client.listLogs(options);
        this.assertLogs(logs);
    }
}
