package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class ModelsTest extends ClientTest {

    @Test
    public void testListModels() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listModels();
        JSONArray models = response.getJSONArray("models");
        Assert.assertNotNull(models);
    }

    @Test
    public void testListModelsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListModelsOptions options = new ListModelsOptions().setMaxResults(30).setNextToken("foo");
        JSONObject response = this.client.listModels(options);
        JSONArray models = response.getJSONArray("models");
        Assert.assertNotNull(models);
    }
}
