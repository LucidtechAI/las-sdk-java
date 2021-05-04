package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WorkflowsTest extends ClientTest {
    private JSONObject createSpecification() {
        JSONObject specification = new JSONObject();
        specification.put("language", "ASL");
        specification.put("version", "1.0.0");
        specification.put("definition", new JSONObject());
        return specification;
    }

    private WorkflowCompletedConfig createCompletedConfig() {
        Map<String, String> environment = new HashMap<String, String>();
        environment.put("TEST_ENV_KEY", "Test Env Value");

        return new WorkflowCompletedConfig()
            .setImageUrl(TestUtils.dockerImageUrl())
            .setEnvironment(environment)
            .setEnvironmentSecrets(new String[] {TestUtils.secretId(), TestUtils.secretId()})
            .setSecretId(TestUtils.secretId());
    }

    private WorkflowErrorConfig createErrorConfig() {
        return new WorkflowErrorConfig()
            .setEmail(TestUtils.email())
            .setManualRetry(true);
    }

    private void assertWorkflowExecution(JSONObject execution) throws IOException {
        Assert.assertTrue(execution.has("completedBy"));
        Assert.assertTrue(execution.has("endTime"));
        Assert.assertTrue(execution.has("executionId"));
        Assert.assertTrue(execution.has("input"));
        Assert.assertTrue(execution.has("logId"));
        Assert.assertTrue(execution.has("output"));
        Assert.assertTrue(execution.has("startTime"));
        Assert.assertTrue(execution.has("status"));
        Assert.assertTrue(execution.has("transitionExecutions"));
        Assert.assertTrue(execution.has("workflowId"));
    }

    private void assertWorkflowExecutions(JSONObject workflowExecutions) throws IOException {
        Assert.assertTrue(workflowExecutions.has("nextToken"));
        Assert.assertTrue(workflowExecutions.has("executions"));

        for (Object workflowExecution: workflowExecutions.getJSONArray("executions")) {
            this.assertWorkflowExecution((JSONObject) workflowExecution);
        }
    }

    private void assertWorkflow(JSONObject workflow) throws IOException {
        Assert.assertTrue(workflow.has("completedConfig"));
        Assert.assertTrue(workflow.has("description"));
        Assert.assertTrue(workflow.has("errorConfig"));
        Assert.assertTrue(workflow.has("name"));
        Assert.assertTrue(workflow.has("workflowId"));
    }

    private void assertWorkflows(JSONObject workflows) throws IOException {
        Assert.assertTrue(workflows.has("nextToken"));
        Assert.assertTrue(workflows.has("workflows"));

        for (Object workflow: workflows.getJSONArray("workflows")) {
            this.assertWorkflow((JSONObject) workflow);
        }
    }

    @Test
    public void testCreateWorkflow() throws IOException, APIException, MissingAccessTokenException {
        JSONObject workflow = this.client.createWorkflow(this.createSpecification());
        this.assertWorkflow(workflow);
    }

    @Test
    public void testCreateWorkflowWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateWorkflowOptions options = new CreateWorkflowOptions()
            .setName("Workflow Name")
            .setDescription("Workflow Description")
            .setCompletedConfig(createCompletedConfig())
            .setErrorConfig(createErrorConfig());

        JSONObject workflow = this.client.createWorkflow(this.createSpecification(), options);
        this.assertWorkflow(workflow);
    }

    @Test
    public void testListWorkflows() throws IOException, APIException, MissingAccessTokenException {
        JSONObject workflows = this.client.listWorkflows();
        this.assertWorkflows(workflows);
    }

    @Test
    public void testListWorkflowsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListWorkflowsOptions options = new ListWorkflowsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject workflows = this.client.listWorkflows(options);
        this.assertWorkflows(workflows);
    }

    @Test
    public void testUpdateWorkflow() throws IOException, APIException, MissingAccessTokenException {
        UpdateWorkflowOptions options = new UpdateWorkflowOptions()
            .setName("Workflow Name")
            .setDescription("Workflow Description");
        JSONObject workflow = this.client.updateWorkflow(TestUtils.workflowId(), options);
        this.assertWorkflow(workflow);
    }

    @Test
    public void testGetWorkflow() throws IOException, APIException, MissingAccessTokenException {
        JSONObject workflow = this.client.getWorkflow(TestUtils.workflowId());
        this.assertWorkflow(workflow);
    }

    @Test
    public void testExecuteWorkflow() throws IOException, APIException, MissingAccessTokenException {
        JSONObject content = new JSONObject();
        content.put("input", new JSONObject());
        JSONObject execution = this.client.executeWorkflow(TestUtils.workflowId(), content);
        this.assertWorkflowExecution(execution);
    }

    @Test
    public void testDeleteWorkflow() throws IOException, APIException, MissingAccessTokenException {
        JSONObject workflow = this.client.deleteWorkflow(TestUtils.workflowId());
        this.assertWorkflow(workflow);
    }

    @Test
    public void testListWorkflowExecutions() throws IOException, APIException, MissingAccessTokenException {
        JSONObject executions = this.client.listWorkflowExecutions(TestUtils.workflowId());
        this.assertWorkflowExecutions(executions);
    }

    @Test
    public void testListWorkflowExecutionsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        List<WorkflowExecutionStatus> status = Arrays.asList(WorkflowExecutionStatus.SUCCEEDED);
        ListWorkflowExecutionsOptions options = new ListWorkflowExecutionsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken")
            .setStatus(status)
            .setSortBy("endTime")
            .setOrder(Order.ASCENDING);
        JSONObject executions = this.client.listWorkflowExecutions(TestUtils.workflowId(), options);
        this.assertWorkflowExecutions(executions);
    }

    @Test
    public void testDeleteWorkflowExecution() throws IOException, APIException, MissingAccessTokenException {
        JSONObject execution = this.client.deleteWorkflowExecution(
            TestUtils.workflowId(),
            TestUtils.workflowExecutionId()
        );
        this.assertWorkflowExecution(execution);
    }

}
