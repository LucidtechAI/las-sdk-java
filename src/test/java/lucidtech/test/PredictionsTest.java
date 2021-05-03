package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class PredictionsTest extends ClientTest {
    private void assertPrediction(JSONObject prediction) throws IOException {
        Assert.assertTrue(prediction.has("predictionId"));
        Assert.assertTrue(prediction.has("documentId"));
        Assert.assertTrue(prediction.has("modelId"));
        Assert.assertTrue(prediction.has("predictions"));
        Assert.assertTrue(prediction.has("timestamp"));
        Assert.assertTrue(prediction.has("inferenceTime"));
    }

    private void assertPredictions(JSONObject predictions) throws IOException {
        Assert.assertTrue(predictions.has("nextToken"));
        Assert.assertTrue(predictions.has("predictions"));

        for (Object prediction: predictions.getJSONArray("predictions")) {
            this.assertPrediction((JSONObject) prediction);
        }
    }

    @Test
    public void testCreatePrediction() throws IOException, APIException, MissingAccessTokenException {
        JSONObject prediction = this.client.createPrediction(TestUtils.documentId(), TestUtils.modelId());
        this.assertPrediction(prediction);
    }

    @Test
    public void testCreatePredictionWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreatePredictionOptions options = new CreatePredictionOptions()
            .setMaxPages(3)
            .setAutoRotate(true)
            .setImageQuality(ImageQuality.LOW);
        JSONObject prediction = this.client.createPrediction(TestUtils.documentId(), TestUtils.modelId(), options);
        this.assertPrediction(prediction);
    }

    @Test
    public void testListPredictions() throws IOException, APIException, MissingAccessTokenException {
        JSONObject predictions = this.client.listPredictions();
        this.assertPredictions(predictions);
    }

    @Test
    public void testListPredictionsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListPredictionsOptions options = new ListPredictionsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject predictions = this.client.listPredictions(options);
        this.assertPredictions(predictions);
    }
}
