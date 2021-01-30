package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class BatchesTest {

    private Client client;

    @Before
    public void setUp() throws MissingCredentialsException {
        Credentials credentials = new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010");
        this.client = new Client(credentials);
    }

    private void assertBatch(JSONObject batch) throws IOException {
        Assert.assertTrue(batch.has("batchId"));
        Assert.assertTrue(batch.has("name"));
        Assert.assertTrue(batch.has("description"));
    }

    @Test
    public void testCreateBatch() throws IOException, APIException, MissingAccessTokenException {
        JSONObject batch = this.client.createBatch();
        this.assertBatch(batch);
    }

    @Test
    public void testCreateBatchWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateBatchOptions options = new CreateBatchOptions().setName("foo").setDescription("bar");
        JSONObject batch = this.client.createBatch(options);
        this.assertBatch(batch);
    }

}
