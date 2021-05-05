package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class ModelsTest extends ClientTest {
    private void assertModel(JSONObject model) throws IOException {
        Assert.assertTrue(model.has("createdTime"));
        Assert.assertTrue(model.has("description"));
        Assert.assertTrue(model.has("fieldConfig"));
        Assert.assertTrue(model.has("height"));
        Assert.assertTrue(model.has("modelId"));
        Assert.assertTrue(model.has("name"));
        Assert.assertTrue(model.has("preprocessConfig"));
        Assert.assertTrue(model.has("status"));
        Assert.assertTrue(model.has("updatedTime"));
        Assert.assertTrue(model.has("width"));
    }

    private void assertModels(JSONObject models) throws IOException {
        Assert.assertTrue(models.has("models"));
        Assert.assertTrue(models.has("nextToken"));

        for (Object model: models.getJSONArray("models")) {
            this.assertModel((JSONObject) model);
        }
    }

    @Test
    public void testListModels() throws IOException, APIException, MissingAccessTokenException {
        JSONObject models = this.client.listModels();
        this.assertModels(models);
    }

    @Test
    public void testListModelsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListModelsOptions options = new ListModelsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject models = this.client.listModels(options);
        this.assertModels(models);
    }
}
