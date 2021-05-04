package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransitionsTest extends ClientTest {
    public ManualTransitionParameters createManualParameters() {
        Map<String, String> assets = new HashMap<String, String>();
        assets.put("jsRemoteComponent", TestUtils.assetId());
        ManualTransitionParameters parameters = new ManualTransitionParameters()
            .setAssets(assets);
        return parameters;
    }

    private DockerTransitionParameters createDockerParameters() {
        Map<String, String> environment = new HashMap<String, String>();
        environment.put("TEST_ENV_KEY", "Test Env Value");

        DockerTransitionParameters parameters = new DockerTransitionParameters()
            .setImageUrl(TestUtils.dockerImageUrl())
            .setMemory(512)
            .setCpu(256)
            .setEnvironment(environment)
            .setEnvironmentSecrets(new String[] {TestUtils.secretId(), TestUtils.secretId()})
            .setSecretId(TestUtils.secretId());
        return parameters;
    }

    private void assertTransitionExecution(JSONObject execution) throws IOException {
        Assert.assertTrue(execution.has("completedBy"));
        Assert.assertTrue(execution.has("endTime"));
        Assert.assertTrue(execution.has("executionId"));
        Assert.assertTrue(execution.has("input"));
        Assert.assertTrue(execution.has("logId"));
        Assert.assertTrue(execution.has("startTime"));
        Assert.assertTrue(execution.has("status"));
        Assert.assertTrue(execution.has("transitionId"));
    }

    private void assertTransitionExecutions(JSONObject transitionExecutions) throws IOException {
        Assert.assertTrue(transitionExecutions.has("nextToken"));
        Assert.assertTrue(transitionExecutions.has("executions"));

        for (Object transitionExecution: transitionExecutions.getJSONArray("executions")) {
            this.assertTransitionExecution((JSONObject) transitionExecution);
        }
    }

    private void assertTransition(JSONObject transition) throws IOException {
        Assert.assertTrue(transition.has("description"));
        Assert.assertTrue(transition.has("inputJsonSchema"));
        Assert.assertTrue(transition.has("name"));
        Assert.assertTrue(transition.has("outputJsonSchema"));
        Assert.assertTrue(transition.has("parameters"));
        Assert.assertTrue(transition.has("transitionId"));
    }

    private void assertTransitions(JSONObject transitions) throws IOException {
        Assert.assertTrue(transitions.has("nextToken"));
        Assert.assertTrue(transitions.has("transitions"));

        for (Object transition: transitions.getJSONArray("transitions")) {
            this.assertTransition((JSONObject) transition);
        }
    }

    @Test
    public void testCreateManualTransition() throws IOException, APIException, MissingAccessTokenException {
        CreateTransitionOptions options = new CreateTransitionOptions()
            .setName("Transition Name")
            .setDescription("Transition Description")
            .setInputJsonSchema(TestUtils.schema())
            .setOutputJsonSchema(TestUtils.schema())
            .setParameters(this.createManualParameters());
        JSONObject transition = this.client.createTransition(TransitionType.MANUAL, options);
        this.assertTransition(transition);
    }

    @Test
    public void testCreateDockerTransition() throws IOException, APIException, MissingAccessTokenException {
        CreateTransitionOptions options = new CreateTransitionOptions()
            .setName("Transition Name")
            .setDescription("Transition Description")
            .setInputJsonSchema(TestUtils.schema())
            .setOutputJsonSchema(TestUtils.schema())
            .setParameters(this.createDockerParameters());
        JSONObject transition = this.client.createTransition(TransitionType.DOCKER, options);
        this.assertTransition(transition);
    }

    @Test
    public void testListTransitions() throws IOException, APIException, MissingAccessTokenException {
        JSONObject transitions = this.client.listTransitions();
        this.assertTransitions(transitions);
    }

    @Test
    public void testListTransitionsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListTransitionsOptions options = new ListTransitionsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken")
            .setTransitionType(TransitionType.MANUAL);
        JSONObject transitions = this.client.listTransitions(options);
        this.assertTransitions(transitions);
    }

    @Test
    public void testGetTransition() throws IOException, APIException, MissingAccessTokenException {
        JSONObject transition = this.client.getTransition(TestUtils.transitionId());
        this.assertTransition(transition);
    }

    @Test
    public void testUpdateTransition() throws IOException, APIException, MissingAccessTokenException {
        UpdateTransitionOptions options = new UpdateTransitionOptions()
            .setName("Transition Name")
            .setDescription("Transition Description")
            .setInputJsonSchema(TestUtils.schema())
            .setOutputJsonSchema(TestUtils.schema());
        JSONObject transition = this.client.updateTransition(TestUtils.transitionId(), options);
        this.assertTransition(transition);
    }

    @Test
    public void testDeleteTransition() throws IOException, APIException, MissingAccessTokenException {
        JSONObject transition = this.client.deleteTransition(TestUtils.transitionId());
        this.assertTransition(transition);
    }

    @Test
    public void testExecuteTransition() throws IOException, APIException, MissingAccessTokenException {
        JSONObject execution = this.client.executeTransition(TestUtils.transitionId());
        this.assertTransitionExecution(execution);
    }

    @Test
    public void testListTransitionExecutions() throws IOException, APIException, MissingAccessTokenException {
        JSONObject executions = this.client.listTransitionExecutions(TestUtils.transitionId());
        this.assertTransitionExecutions(executions);
    }

    @Test
    public void testListTransitionExecutionsWithExecutionId() throws IOException, APIException, MissingAccessTokenException {
        ListTransitionExecutionsOptions options = new ListTransitionExecutionsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken")
            .setExecutionId(TestUtils.transitionExecutionId())
            .setSortBy("endTime")
            .setOrder(Order.DESCENDING);
        JSONObject executions = this.client.listTransitionExecutions(TestUtils.transitionId(), options);
        this.assertTransitionExecutions(executions);
    }

    @Test
    public void testListTransitionExecutionsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        List<TransitionExecutionStatus> status = Arrays.asList(TransitionExecutionStatus.SUCCEEDED);
        ListTransitionExecutionsOptions options = new ListTransitionExecutionsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken")
            .setStatus(status)
            .setSortBy("endTime")
            .setOrder(Order.ASCENDING);
        JSONObject executions = this.client.listTransitionExecutions(TestUtils.transitionId(), options);
        this.assertTransitionExecutions(executions);
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
            .setOutput(new JSONObject(){{ put("out", "foo"); }})
            .setStartTime(TestUtils.isoDateTime());
        JSONObject execution = this.client.updateTransitionExecution(
            TestUtils.transitionId(),
            TestUtils.transitionExecutionId(),
            TransitionExecutionStatus.SUCCEEDED,
            options
        );
        this.assertTransitionExecution(execution);
    }

    @Test
    public void testUpdateTransitionExecutionFailed() throws IOException, APIException, MissingAccessTokenException {
        UpdateTransitionExecutionOptions options = new UpdateTransitionExecutionOptions()
            .setError(new JSONObject(){{ put("message", "foo"); }})
            .setStartTime(TestUtils.isoDateTime());
        JSONObject execution = this.client.updateTransitionExecution(
            TestUtils.transitionId(),
            TestUtils.transitionExecutionId(),
            TransitionExecutionStatus.FAILED,
            options
        );
        this.assertTransitionExecution(execution);
    }

    @Test
    public void testSendHeartbeat() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.sendHeartbeat(TestUtils.transitionId(), TestUtils.transitionExecutionId());
        Assert.assertTrue(response.has("Your request executed successfully"));
    }

}
