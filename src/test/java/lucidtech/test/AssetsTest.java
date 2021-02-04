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
        Assert.assertTrue(asset.has("name"));
        Assert.assertTrue(asset.has("description"));
    }

    @Test
    public void testCreateAsset() throws IOException, APIException, MissingAccessTokenException {
        JSONObject asset = this.client.createAsset(TestUtils.content());
        this.assertAsset(asset);
    }

    @Test
    public void testCreateAssetWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateAssetOptions options = new CreateAssetOptions().setName("foo").setDescription("bar");
        JSONObject asset = this.client.createAsset(TestUtils.content(), options);
        this.assertAsset(asset);
    }

    @Test
    public void testCreateAssetWithInputStreamAndOptions() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(TestUtils.content());
        CreateAssetOptions options = new CreateAssetOptions().setName("foo").setDescription("bar");
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
        JSONObject response = this.client.listAssets();
        JSONArray assets = response.getJSONArray("assets");
        Assert.assertNotNull(assets);
    }

    @Test
    public void testListAssetsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListAssetsOptions options = new ListAssetsOptions().setMaxResults(30).setNextToken("foo");
        JSONObject response = this.client.listAssets(options);
        JSONArray assets = response.getJSONArray("assets");
        Assert.assertNotNull(assets);
    }

    @Test
    public void testGetAsset() throws IOException, APIException, MissingAccessTokenException {
        JSONObject asset = this.client.getAsset(TestUtils.assetId());
        this.assertAsset(asset);
    }

    @Test
    public void testUpdateAsset() throws IOException, APIException, MissingAccessTokenException {
        UpdateAssetOptions options = new UpdateAssetOptions()
        .setName("foo")
        .setDescription("bar")
        .setContent(TestUtils.content());
        JSONObject asset = this.client.updateAsset(TestUtils.assetId(), options);
        this.assertAsset(asset);
    }
}
