package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.util.Properties;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.StreamSupport;


public class AssetsTest {

    private Client client;
    private Service service;

    private byte[] content;

    @Before
    public void setUp() throws MissingCredentialsException {
        this.service = new Service();

        Credentials credentials = new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010");

        this.client = new Client(credentials);
        this.content = "0xe04fd020ea3a6910a2d808002b30309d".getBytes();

    }


    private void assertAsset(JSONObject asset) throws IOException {
        Assert.assertTrue(asset.has("assetId"));
        Assert.assertTrue(asset.has("content"));
        Assert.assertTrue(asset.has("name"));
        Assert.assertTrue(asset.has("description"));
    }

    @Test
    public void testCreateAsset() throws IOException, APIException, MissingAccessTokenException {
        JSONObject asset = this.client.createAsset(this.content);
        this.assertAsset(asset);
    }

    @Test
    public void testCreateAssetWithOptions() throws IOException, APIException, MissingAccessTokenException {
        JSONObject asset = this.client.createAsset(this.content);
        this.assertAsset(asset);
    }

    @Test
    public void testCreateAssetWithInputStreamAndOptions() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(this.content);
        JSONObject asset = this.client.createAsset(input);
        this.assertAsset(asset);

    }
    @Test
    public void testCreateAssetWithInputStream() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(this.content);
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
        JSONObject response = this.client.listAssets();
        JSONArray assets = response.getJSONArray("assets");
        Assert.assertNotNull(assets);
    }

    @Test
    public void testGetAsset() throws IOException, APIException, MissingAccessTokenException {
        JSONObject asset = this.client.getAsset(this.service.assetId());
        this.assertAsset(asset);
    }

    @Test
    public void testUpdateAsset() throws IOException, APIException, MissingAccessTokenException {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("name", "foo");
        JSONObject asset = this.client.updateAsset(this.service.assetId(), options);
        this.assertAsset(asset);
    }

    private String getResourcePath(String relativePath) {
        System.out.println("relativePath : " + relativePath);
        return getClass().getResource(relativePath).getFile();
    }

    private String[] toArray(String s) {
        return Arrays.stream(s.split(",")).map(String::trim).toArray(String[]::new); }

    private JSONObject createField(String label, String value) {
        JSONObject field = new JSONObject();
        field.put("label", label);
        field.put("value", value);
        return field;
    }
}
