package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

public class TransitionsTest extends ClientTest {

    public JSONObject getManualParameters(){
        JSONObject assets = new JSONObject();
        assets.put("jsRemoteComponent", TestUtils.assetId());
        assets.put("thresholds", TestUtils.assetId());
        JSONObject parameters = new JSONObject();
        parameters.put("assets", assets);
        return parameters;
    }

    public JSONObject getDockerParameters(){
        JSONObject parameters = new JSONObject();
        parameters.put("imageUrl", "foo");
        parameters.put("secretId", TestUtils.secretId());
        return parameters;
    }

    private void assertTransitionExecution(JSONObject execution) throws IOException {
        Assert.assertTrue(execution.has("transitionId"));
        Assert.assertTrue(execution.has("executionId"));
        Assert.assertTrue(execution.has("status"));
        Assert.assertTrue(execution.has("input"));
        Assert.assertTrue(execution.has("completedBy"));
        Assert.assertTrue(execution.has("startTime"));
        Assert.assertTrue(execution.has("endTime"));
        Assert.assertTrue(execution.has("logId"));
    }

    private void assertTransition(JSONObject transition) throws IOException {
        Assert.assertTrue(transition.has("transitionId"));
        Assert.assertTrue(transition.has("inputJsonSchema"));
        Assert.assertTrue(transition.has("outputJsonSchema"));
        Assert.assertTrue(transition.has("parameters"));
        Assert.assertTrue(transition.has("description"));
        Assert.assertTrue(transition.has("name"));
    }

    @Test
    public void testCreateManualTransition() throws IOException, APIException, MissingAccessTokenException {
        CreateTransitionOptions options = new CreateTransitionOptions().setParameters(this.getManualParameters());
        JSONObject transition = this.client.createTransition("manual", options);
        this.assertTransition(transition);
    }

    @Test
    public void testCreateDockerTransition() throws IOException, APIException, MissingAccessTokenException {
        CreateTransitionOptions options = new CreateTransitionOptions().setParameters(this.getDockerParameters());
        JSONObject transition = this.client.createTransition("docker", options);
        this.assertTransition(transition);
    }

    @Test
    public void testCreateManualTransitionWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateTransitionOptions options = new CreateTransitionOptions()
        .setName("foo")
        .setDescription("bar")
        .setInputJsonSchema(TestUtils.schema())
        .setOutputJsonSchema(TestUtils.schema())
        .setParameters(this.getManualParameters());
        JSONObject transition = this.client.createTransition("manual", options);
        this.assertTransition(transition);
    }

    @Test
    public void testCreateDockerTransitionWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateTransitionOptions options = new CreateTransitionOptions()
        .setName("foo")
        .setDescription("bar")
        .setInputJsonSchema(TestUtils.schema())
        .setOutputJsonSchema(TestUtils.schema())
        .setParameters(this.getDockerParameters());
        JSONObject transition = this.client.createTransition("docker", options);
        this.assertTransition(transition);
    }

    @Test
    public void testListTransitions() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listTransitions();
        JSONArray transitions = response.getJSONArray("transitions");
        Assert.assertNotNull(transitions);
    }

    @Test
    public void testListTransitionsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListTransitionsOptions options = new ListTransitionsOptions()
        .setMaxResults(30)
        .setNextToken("foo")
        .setTransitionType(TransitionType.MANUAL);
        JSONObject response = this.client.listTransitions(options);
        JSONArray transitions = response.getJSONArray("transitions");
        Assert.assertNotNull(transitions);
    }

    @Test
    public void testUpdateTransition() throws IOException, APIException, MissingAccessTokenException {
        UpdateTransitionOptions options = new UpdateTransitionOptions()
        .setName("foo")
        .setDescription("bar")
        .setInputJsonSchema(TestUtils.schema())
        .setOutputJsonSchema(TestUtils.schema());
        JSONObject transition = this.client.updateTransition(TestUtils.transitionId(), options);
        this.assertTransition(transition);
    }

    @Test
    public void testUpdateTransitionWithOptions() throws IOException, APIException, MissingAccessTokenException {
        UpdateTransitionOptions options = new UpdateTransitionOptions().setName("foo");
        JSONObject transition = this.client.updateTransition(TestUtils.transitionId(), options);
        this.assertTransition(transition);
    }

    @Test
    public void testExecuteTransition() throws IOException, APIException, MissingAccessTokenException {
        JSONObject execution = this.client.executeTransition(TestUtils.transitionId());
        this.assertTransitionExecution(execution);
    }

    @Test
    public void testListTransitionExecutions() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listTransitionExecutions(TestUtils.transitionId());
        JSONArray executions = response.getJSONArray("executions");
        Assert.assertNotNull(executions);
    }

    @Test
    public void testListTransitionExecutionsWithExecutionId() throws IOException, APIException, MissingAccessTokenException {
        ListTransitionExecutionsOptions options = new ListTransitionExecutionsOptions()
        .setMaxResults(30)
        .setNextToken("foo")
        .setExecutionId(TestUtils.transitionExecutionId())
        .setSortBy("endTime")
        .setOrder(Order.DESCENDING);
        JSONObject response = this.client.listTransitionExecutions(TestUtils.transitionId(), options);
        JSONArray executions = response.getJSONArray("executions");
        Assert.assertNotNull(executions);
    }

    @Test
    public void testListTransitionExecutionsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        List<TransitionExecutionStatus> status = Arrays.asList(TransitionExecutionStatus.SUCCEEDED);
        ListTransitionExecutionsOptions options = new ListTransitionExecutionsOptions()
        .setMaxResults(30)
        .setNextToken("foo")
        .setStatus(status)
        .setSortBy("endTime")
        .setOrder(Order.ASCENDING);
        JSONObject response = this.client.listTransitionExecutions(TestUtils.transitionId(), options);
        JSONArray executions = response.getJSONArray("executions");
        Assert.assertNotNull(executions);
    }

    @Test
    public void testGetTransitionExecution() throws IOException, APIException, MissingAccessTokenException {
        JSONObject execution = this.client.getTransitionExecution(
            TestUtils.transitionId(),
            TestUtils.transitionExecutionId()
        );
        this.assertTransitionExecution(execution);
    }

    @Test
    public void testUpdateTransitionExecutionSucceeded() throws IOException, APIException, MissingAccessTokenException {
        UpdateTransitionExecutionOptions options = new UpdateTransitionExecutionOptions()
        .setOutput(new JSONObject(){{ put("out", "foo"); }});
        JSONObject execution = this.client.updateTransitionExecution(
            TestUtils.transitionId(),
            TestUtils.transitionExecutionId(),
            "succeeded",
            options
        );
        this.assertTransitionExecution(execution);
    }

    @Test
    public void testUpdateTransitionExecutionFailed() throws IOException, APIException, MissingAccessTokenException {
        UpdateTransitionExecutionOptions options = new UpdateTransitionExecutionOptions()
        .setError(new JSONObject(){{ put("message", "foo"); }});
        JSONObject execution = this.client.updateTransitionExecution(
            TestUtils.transitionId(),
            TestUtils.transitionExecutionId(),
            "failed",
            options
        );
        this.assertTransitionExecution(execution);
    }

}
