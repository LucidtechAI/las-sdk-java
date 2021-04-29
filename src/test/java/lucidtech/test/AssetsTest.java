package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;


public class AssetsTest extends ClientTest {
    private void assertAsset(JSONObject asset) throws IOException {
        Assert.assertTrue(asset.has("assetId"));
        Assert.assertTrue(asset.has("content"));
        Assert.assertTrue(asset.has("description"));
        Assert.assertTrue(asset.has("name"));
    }

    private void assertAssets(JSONObject assets) throws IOException {
        Assert.assertTrue(assets.has("assets"));
        Assert.assertTrue(assets.has("nextToken"));

        for (Object asset: assets.getJSONArray("assets")) {
            this.assertAsset((JSONObject) asset);
        }
    }

    @Test
    public void testCreateAsset() throws IOException, APIException, MissingAccessTokenException {
        JSONObject asset = this.client.createAsset(TestUtils.content());
        this.assertAsset(asset);
    }

    @Test
    public void testCreateAssetWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateAssetOptions options = new CreateAssetOptions()
            .setName("Asset Name")
            .setDescription("Asset Description");
        JSONObject asset = this.client.createAsset(TestUtils.content(), options);
        this.assertAsset(asset);
    }

    @Test
    public void testCreateAssetWithInputStreamAndOptions() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(TestUtils.content());
        CreateAssetOptions options = new CreateAssetOptions()
            .setName("Asset Name")
            .setDescription("Asset Description");
        JSONObject asset = this.client.createAsset(input, options);
        this.assertAsset(asset);

    }
    @Test
    public void testCreateAssetWithInputStream() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(TestUtils.content());
        JSONObject asset = this.client.createAsset(input);
        this.assertAsset(asset);
    }

    @Test
    public void testListAssets() throws IOException, APIException, MissingAccessTokenException {
        JSONObject assets = this.client.listAssets();
        this.assertAssets(assets);
    }

    @Test
    public void testListAssetsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListAssetsOptions options = new ListAssetsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject assets = this.client.listAssets(options);
        this.assertAssets(assets);
    }

    @Test
    public void testGetAsset() throws IOException, APIException, MissingAccessTokenException {
        String assetId = TestUtils.assetId();
        JSONObject asset = this.client.getAsset(assetId);
        this.assertAsset(asset);
    }

    @Test
    public void testUpdateAsset() throws IOException, APIException, MissingAccessTokenException {
        String assetId = TestUtils.assetId();
        UpdateAssetOptions options = new UpdateAssetOptions()
            .setName("Asset Name")
            .setDescription("Asset Description")
            .setContent(TestUtils.content());
        JSONObject asset = this.client.updateAsset(assetId, options);
        this.assertAsset(asset);
    }

    @Test
    public void testDeleteAsset() throws IOException, APIException, MissingAccessTokenException {
        String assetId = TestUtils.assetId();
        JSONObject asset = this.client.deleteAsset(assetId);
        this.assertAsset(asset);
    }
}
