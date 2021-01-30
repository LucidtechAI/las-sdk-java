package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;


public class TransitionsTest {

    private Client client;
    private Service service;

    public JSONObject getManualParameters(){
        JSONObject assets = new JSONObject();
        assets.put("jsRemoteComponent", this.service.assetId());
        assets.put("thresholds", this.service.assetId());
        JSONObject parameters = new JSONObject();
        parameters.put("assets", assets);
        return parameters;
    }

    public JSONObject getDockerParameters(){
        JSONObject parameters = new JSONObject();
        parameters.put("imageUrl", "foo");
        parameters.put("secretId", this.service.secretId());
        return parameters;
    }

    @Before
    public void setUp() throws MissingCredentialsException {
        this.service = new Service();
        Credentials credentials = new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010");
        this.client = new Client(credentials);
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
        .setInSchema(this.service.schema())
        .setOutSchema(this.service.schema())
        .setParameters(this.getManualParameters());
        JSONObject transition = this.client.createTransition("manual", options);
        this.assertTransition(transition);
    }

    @Test
    public void testCreateDockerTransitionWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateTransitionOptions options = new CreateTransitionOptions()
        .setName("foo")
        .setDescription("bar")
        .setInSchema(this.service.schema())
        .setOutSchema(this.service.schema())
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
        .setTransitionType("manual");
        JSONObject response = this.client.listTransitions(options);
        JSONArray transitions = response.getJSONArray("transitions");
        Assert.assertNotNull(transitions);
    }

/*
    @Test
    public void testUpdateTransition() throws IOException, APIException, MissingAccessTokenException {
        UpdateTransitionOptions options = new UpdateTransitionOptions()
        .setName("foo")
        .setDescription("bar")
        .setContent(this.service.content());
        JSONObject transition = this.client.updateTransition(this.service.transitionId(), options);
        this.assertTransition(transition);
    }
    */
}