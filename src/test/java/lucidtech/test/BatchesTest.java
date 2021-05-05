package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class BatchesTest extends ClientTest {

    private void assertBatch(JSONObject batch) throws IOException {
        Assert.assertTrue(batch.has("batchId"));
        Assert.assertTrue(batch.has("containsPersonallyIdentifiableInformation"));
        Assert.assertTrue(batch.has("createdTime"));
        Assert.assertTrue(batch.has("description"));
        Assert.assertTrue(batch.has("name"));
        Assert.assertTrue(batch.has("numDocuments"));
        Assert.assertTrue(batch.has("retentionInDays"));
        Assert.assertTrue(batch.has("storageLocation"));
    }

    private void assertBatches(JSONObject batches) throws IOException {
        Assert.assertTrue(batches.has("batches"));
        Assert.assertTrue(batches.has("nextToken"));

        for (Object batch: batches.getJSONArray("batches")) {
            this.assertBatch((JSONObject) batch);
        }
    }

    @Test
    public void testCreateBatch() throws IOException, APIException, MissingAccessTokenException {
        JSONObject batch = this.client.createBatch();
        this.assertBatch(batch);
    }

    @Test
    public void testCreateBatchWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateBatchOptions options = new CreateBatchOptions()
            .setName("Batch Name")
            .setDescription("Batch Description");
        JSONObject batch = this.client.createBatch(options);
        this.assertBatch(batch);
    }

    @Test
    public void testListBatches() throws IOException, APIException, MissingAccessTokenException {
        JSONObject batches = this.client.listBatches();
        this.assertBatches(batches);
    }

    @Test
    public void testListBatchesWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListBatchesOptions options = new ListBatchesOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject batches = this.client.listBatches(options);
        this.assertBatches(batches);
    }

    @Test
    public void testDeleteBatch() throws IOException, APIException, MissingAccessTokenException {
        String batchId = TestUtils.batchId();
        JSONObject batch = this.client.deleteBatch(batchId);
        this.assertBatch(batch);
    }

    @Test
    public void testDeleteBatchWithDocuments() throws IOException, APIException, MissingAccessTokenException {
        String batchId = TestUtils.batchId();
        JSONObject batch = this.client.deleteBatch(batchId, true);
        this.assertBatch(batch);
    }
}
