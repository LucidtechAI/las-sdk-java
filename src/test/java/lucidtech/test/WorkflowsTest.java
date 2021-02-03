package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;


public class WorkflowsTest extends ClientTest {

    private JSONObject specification(){
        JSONObject specification = new JSONObject();
        specification.put("language", "ASL");
        specification.put("version", "1.0.0");
        specification.put("definition", new JSONObject());
        return specification;
    }
    private void assertWorkflowExecution(JSONObject execution) throws IOException {
        Assert.assertTrue(execution.has("workflowId"));
        Assert.assertTrue(execution.has("executionId"));
        Assert.assertTrue(execution.has("status"));
        Assert.assertTrue(execution.has("input"));
        Assert.assertTrue(execution.has("completedBy"));
        Assert.assertTrue(execution.has("startTime"));
        Assert.assertTrue(execution.has("endTime"));
    }

    private void assertWorkflow(JSONObject workflow) throws IOException {
        Assert.assertTrue(workflow.has("workflowId"));
        Assert.assertTrue(workflow.has("description"));
        Assert.assertTrue(workflow.has("name"));
    }

    @Test
    public void testCreateWorkflow() throws IOException, APIException, MissingAccessTokenException {
        JSONObject workflow = this.client.createWorkflow(this.specification());
        this.assertWorkflow(workflow);
    }

    @Test
    public void testCreateWorkflowWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateWorkflowOptions options = new CreateWorkflowOptions()
        .setName("foo")
        .setDescription("bar")
        .setErrorConfig(new JSONObject(){{ put("email", TestUtils.email()); }});

        JSONObject workflow = this.client.createWorkflow(this.specification(), options);
        this.assertWorkflow(workflow);
    }

    @Test
    public void testListWorkflows() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listWorkflows();
        JSONArray workflows = response.getJSONArray("workflows");
        Assert.assertNotNull(workflows);
    }

    @Test
    public void testListWorkflowsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListWorkflowsOptions options = new ListWorkflowsOptions()
        .setMaxResults(30)
        .setNextToken("foo");
        JSONObject response = this.client.listWorkflows(options);
        JSONArray workflows = response.getJSONArray("workflows");
        Assert.assertNotNull(workflows);
    }

    @Test
    public void testUpdateWorkflow() throws IOException, APIException, MissingAccessTokenException {
        UpdateWorkflowOptions options = new UpdateWorkflowOptions()
        .setName("foo")
        .setDescription("bar");
        JSONObject workflow = this.client.updateWorkflow(TestUtils.workflowId(), options);
        this.assertWorkflow(workflow);
    }

    @Test
    public void testExecuteWorkflow() throws IOException, APIException, MissingAccessTokenException {
        JSONObject content  = new JSONObject();
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
        JSONObject response = this.client.listWorkflowExecutions(TestUtils.workflowId());
        JSONArray executions = response.getJSONArray("executions");
        Assert.assertNotNull(executions);
    }

    @Test
    public void testListWorkflowExecutionsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        List<String> status = Arrays.asList("succeeded");
        ListWorkflowExecutionsOptions options = new ListWorkflowExecutionsOptions()
        .setMaxResults(30)
        .setNextToken("foo")
        .setStatus(status)
        .setSortBy("endTime")
        .setOrder("ascending");
        JSONObject response = this.client.listWorkflowExecutions(TestUtils.workflowId(), options);
        JSONArray executions = response.getJSONArray("executions");
        Assert.assertNotNull(executions);
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
