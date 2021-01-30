package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class PredictionsTest {

    private Client client;
    private Service service;

    @Before
    public void setUp() throws MissingCredentialsException {
        this.service = new Service();
        Credentials credentials = new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010");
        this.client = new Client(credentials);
    }

    private void assertPrediction(JSONObject prediction) throws IOException {
        Assert.assertTrue(prediction.has("predictionId"));
        Assert.assertTrue(prediction.has("predictions"));
    }

    @Test
    public void testCreatePrediction() throws IOException, APIException, MissingAccessTokenException {
        JSONObject prediction = this.client.createPrediction(this.service.documentId(), this.service.modelId());
        this.assertPrediction(prediction);
    }

    @Test
    public void testCreatePredictionWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreatePredictionOptions options = new CreatePredictionOptions().setMaxPages(3).setAutoRotate(true);
        JSONObject prediction = this.client.createPrediction(this.service.documentId(), this.service.modelId(), options);
        this.assertPrediction(prediction);
    }

    @Test
    public void testListPredictions() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listPredictions();
        JSONArray predictions = response.getJSONArray("predictions");
        Assert.assertNotNull(predictions);
    }

    @Test
    public void testListPredictionsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListPredictionsOptions options = new ListPredictionsOptions().setMaxResults(30).setNextToken("foo");
        JSONObject response = this.client.listPredictions(options);
        JSONArray predictions = response.getJSONArray("predictions");
        Assert.assertNotNull(predictions);
    }
}