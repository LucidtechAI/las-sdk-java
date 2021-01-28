import ai.lucidtech.las.sdk.Client;
import ai.lucidtech.las.sdk.Credentials;
import ai.lucidtech.las.sdk.ContentType;
import ai.lucidtech.las.sdk.MissingCredentialsException;
import ai.lucidtech.las.sdk.MissingAccessTokenException;
import ai.lucidtech.las.sdk.APIException;
import ai.lucidtech.las.sdk.OptionalNameAndDescription;
import ai.lucidtech.las.sdk.OptionalListResource;

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


public class ClientTest {
    private static final String CONFIG_RELATIVE_PATH = "config.properties.sample";

    private Client client;
    private Properties config;

    private String assetId;
    private String batchId;
    private String consentId;
    private String userId;
    private String logId;
    private String documentId;
    private String modelId;
    private String secretId;
    private List<JSONObject> groundTruth;
    private byte[] content;

    @Before
    public void setUp() throws MissingCredentialsException {
        String configPath = this.getResourcePath(ClientTest.CONFIG_RELATIVE_PATH);

        try(FileInputStream input = new FileInputStream(configPath)) {
            this.config = new Properties();
            this.config.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Credentials credentials = new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010");

        this.client = new Client(credentials);

        this.assetId = "las:asset:" + UUID.randomUUID().toString().replace("-", "");
        this.batchId = "las:batch:" + UUID.randomUUID().toString().replace("-", "");
        this.consentId = "las:consent:" + UUID.randomUUID().toString().replace("-", "");
        this.documentId = "las:document:" + UUID.randomUUID().toString().replace("-", "");
        this.logId = "las:log:" + UUID.randomUUID().toString().replace("-", "");
        this.modelId = "las:model:" + UUID.randomUUID().toString().replace("-", "");
        this.secretId = "las:secret:" + UUID.randomUUID().toString().replace("-", "");
        this.userId = "las:user:" + UUID.randomUUID().toString().replace("-", "");
        Path path = Paths.get(this.getResourcePath("example.jpeg"));

        try {
            this.content = Files.readAllBytes(path); // fails with prism because of file size
        } catch (IOException ex) {}

        this.groundTruth = Arrays.asList(
            this.createField("total_amount", "123.00"),
            this.createField("purchase_date", "2019-05-23")
        );
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
        OptionalNameAndDescription options = new OptionalNameAndDescription().setName("foo");
        JSONObject asset = this.client.createAsset(this.content, options);
        this.assertAsset(asset);
    }

    @Test
    public void testCreateAssetWithInputStreamAndOptions() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(this.content);
        OptionalNameAndDescription options = new OptionalNameAndDescription().setName("foo");
        JSONObject asset = this.client.createAsset(input, options);
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
        OptionalListResource options = new OptionalListResource().setMaxResults(30);
        JSONObject response = this.client.listAssets(options);
        JSONArray assets = response.getJSONArray("assets");
        Assert.assertNotNull(assets);
    }

    @Test
    public void testGetAsset() throws IOException, APIException, MissingAccessTokenException {
        JSONObject asset = this.client.getAsset(this.assetId);
        this.assertAsset(asset);
    }

    @Test
    public void testUpdateAsset() throws IOException, APIException, MissingAccessTokenException {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("name", "foo");
        JSONObject asset = this.client.updateAsset(this.assetId, options);
        this.assertAsset(asset);
    }

    @Test
    public void testGetDocument() throws IOException, APIException, MissingAccessTokenException {
        ContentType contentType = ContentType.fromString("image/jpeg");
        JSONObject newDocument = this.client.createDocument(this.content, contentType, this.consentId);
        JSONObject document = this.client.getDocument(newDocument.getString("documentId"));
        Assert.assertTrue(document.has("consentId"));
        Assert.assertTrue(document.has("contentType"));
        Assert.assertTrue(document.has("documentId"));
    }

    @Test
    public void testCreateDocument() throws IOException, APIException, MissingAccessTokenException {
        String[] documentMimeTypes = this.toArray(this.config.getProperty("document.mime.types"));

        for (String documentMimeType : documentMimeTypes) {
            ContentType contentType = ContentType.fromString(documentMimeType);

            JSONObject document = this.client.createDocument(this.content, contentType, this.consentId);
            Assert.assertTrue(document.has("consentId"));
            Assert.assertTrue(document.has("contentType"));
            Assert.assertTrue(document.has("documentId"));
        }
    }

    @Test
    public void testCreateDocumentWithInputStream() throws IOException, APIException, MissingAccessTokenException {
        String[] documentMimeTypes = this.toArray(this.config.getProperty("document.mime.types"));

        for (String documentMimeType : documentMimeTypes) {
            ContentType contentType = ContentType.fromString(documentMimeType);

            InputStream input = new ByteArrayInputStream(this.content);
            JSONObject document = this.client.createDocument(input, contentType, this.consentId);
            Assert.assertTrue(document.has("consentId"));
            Assert.assertTrue(document.has("contentType"));
            Assert.assertTrue(document.has("documentId"));
        }
    }

    @Test
    public void testCreateDocumentWithOptions() throws IOException, APIException, MissingAccessTokenException {
        String[] documentMimeTypes = this.toArray(this.config.getProperty("document.mime.types"));
        Map<String, Object> options = new HashMap<String, Object>();
        List<JSONObject> fieldList = Arrays.asList(
            this.createField("total_amount", "123.00"),
            this.createField("purchase_date", "2019-05-23")
        );
        JSONArray fields = new JSONArray(fieldList);
        options.put("groundTruth", fields);

        for (String documentMimeType : documentMimeTypes) {
            byte[] content = UUID.randomUUID().toString().getBytes();
            ContentType contentType = ContentType.fromString(documentMimeType);

            JSONObject document = this.client.createDocument(content, contentType, this.consentId, options);
            Assert.assertTrue(document.has("consentId"));
            Assert.assertTrue(document.has("contentType"));
            Assert.assertTrue(document.has("documentId"));
            Assert.assertTrue(document.has("groundTruth"));
            Assert.assertTrue(document.has("batchId"));
        }
    }

    @Test
    public void testCreatePrediction() throws IOException, APIException, MissingAccessTokenException {
        String[] documentPaths = this.toArray(this.config.getProperty("document.paths"));
        String[] mimeTypes = this.toArray(this.config.getProperty("document.mime.types"));

        for (int i = 0; i < documentPaths.length; ++i) {
            String modelId = this.modelId;
            ContentType contentType = ContentType.fromString(mimeTypes[i]);
            JSONObject document = this.client.createDocument(this.content, contentType, this.consentId);
            JSONObject prediction = this.client.createPrediction(document.getString("documentId"), modelId);
            JSONArray fields = prediction.getJSONArray("predictions");
            Assert.assertNotNull(fields);

            StreamSupport.stream(fields.spliterator(), false)
                .map(o -> (JSONObject)o)
                .forEach(field -> {
                    Assert.assertNotNull(field.get("label"));
                    Assert.assertNotNull(field.get("value"));
                    float confidence = field.getFloat("confidence");
                    Assert.assertTrue(confidence >= 0.0);
                    Assert.assertTrue(confidence <= 1.0);
                });
        }
    }

    @Test
    public void testUpdateDocument() throws IOException, APIException, MissingAccessTokenException {
        String[] documentMimeTypes = this.toArray(this.config.getProperty("document.mime.types"));

        for (String documentMimeType : documentMimeTypes) {
            ContentType contentType = ContentType.fromString(documentMimeType);
            JSONObject document = this.client.createDocument(this.content, contentType, this.consentId);

            List<JSONObject> fieldList = Arrays.asList(
                this.createField("total_amount", "123.00"),
                this.createField("purchase_date", "2019-05-23")
            );
            JSONArray groundTruth = new JSONArray(fieldList);

            System.out.println("documentId: " + document.getString("documentId"));
            JSONObject groundTruthResponse = this.client.updateDocument(document.getString("documentId"), groundTruth);

            Assert.assertNotNull(groundTruthResponse.get("documentId"));
            Assert.assertNotNull(groundTruthResponse.get("consentId"));
            JSONArray groundTruthValue = groundTruthResponse.getJSONArray("groundTruth");
            Assert.assertNotNull(groundTruthValue);

            StreamSupport.stream(groundTruthValue.spliterator(), false)
                .map(o -> (JSONObject)o)
                .forEach(groundTruthItem -> {
                    Assert.assertNotNull(groundTruthItem.get("label"));
                    Assert.assertNotNull(groundTruthItem.get("value"));
                });
        }
    }

    @Test
    public void testListDocuments() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listDocuments();
        JSONArray documents = response.getJSONArray("documents");
        Assert.assertNotNull(documents);
    }

    @Test
    public void testListFilteredDocuments() throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> options = new ArrayList<NameValuePair>();
        options.add(new BasicNameValuePair("batchId", this.batchId));
        options.add(new BasicNameValuePair("consentId", this.consentId));
        JSONObject response = this.client.listDocuments(options);
        JSONArray documents = response.getJSONArray("documents");
        Assert.assertNotNull(documents);
    }

    @Test
    public void testCreateBatch() throws IOException, APIException, MissingAccessTokenException {
        String description = "I'm gonna create a new batch, give me a batch id!";
        JSONObject response = this.client.createBatch(description);
        Assert.assertNotNull(response.get("batchId"));
    }

    @Test
    public void testListModels() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listModels();
        JSONArray models = response.getJSONArray("models");
        Assert.assertNotNull(models);
    }

    @Test
    public void testListPredictions() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listPredictions();
        JSONArray predictions = response.getJSONArray("predictions");
        Assert.assertNotNull(predictions);
    }

    @Test
    public void testGetLog() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.getLog(this.logId);
    }

    private void assertSecret(JSONObject secret) throws IOException {
        Assert.assertTrue(secret.has("secretId"));
        Assert.assertTrue(secret.has("name"));
        Assert.assertTrue(secret.has("description"));
    }

    @Test
    public void testCreateSecretWithOptions() throws IOException, APIException, MissingAccessTokenException {
        Map<String, String> data = new HashMap<String, String>(){{ put("username", "foo"); }};
        Map<String, Object> options = new HashMap<String, Object>(){{ put("name", "bar"); }};
        JSONObject secret = this.client.createSecret(data, options);
        this.assertSecret(secret);
    }

    @Test
    public void testCreateSecret() throws IOException, APIException, MissingAccessTokenException {
        Map<String, String> data = new HashMap<String, String>(){{ put("username", "foo"); }};
        JSONObject secret = this.client.createSecret(data);
        this.assertSecret(secret);
    }

    @Test
    public void testListSecrets() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listSecrets();
        JSONArray secrets = response.getJSONArray("secrets");
        Assert.assertNotNull(secrets);
    }

    @Test
    public void testUpdateSecret() throws IOException, APIException, MissingAccessTokenException {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("name", "foo");
        JSONObject secret = this.client.updateSecret(this.secretId, options);
        this.assertSecret(secret);
    }

    @Test
    public void testGetUser() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.getUser(this.userId);
    }

    @Ignore // DELETE requests are not supported yet
    @Test
    public void testDeleteDocuments() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.deleteDocuments();
    }

    @Ignore // DELETE requests are not supported yet
    @Test
    public void testDeleteDocumentsWithConsent() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.deleteDocuments(this.consentId);
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
