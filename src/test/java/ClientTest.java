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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.StreamSupport;


public class ClientTest {
    private static final String CONFIG_RELATIVE_PATH = "config.properties";

    private Client client;
    private Properties config;

    private String batchId;
    private String consentId;
    private String userId;
    private List<JSONObject> feedback;
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

        Credentials credentials = new Credentials(
            System.getenv("TEST_LAS_CLIENT_ID"),
            System.getenv("TEST_LAS_CLIENT_SECRET"),
            System.getenv("TEST_LAS_API_KEY"),
            System.getenv("TEST_LAS_AUTH_ENDPOINT"),
            System.getenv("TEST_LAS_API_ENDPOINT")
        );

        this.client = new Client(credentials);

        this.batchId = UUID.randomUUID().toString();
        this.consentId = UUID.randomUUID().toString();
        this.userId = UUID.randomUUID().toString();
        Path path = Paths.get(this.getResourcePath("example.jpeg"));

        try {
            this.content = Files.readAllBytes(path); // fails with prism because of file size
        } catch (IOException ex) {}

        this.feedback = Arrays.asList(
            this.createField("total_amount", "123.00"),
            this.createField("purchase_date", "2019-05-23")
        );
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
    public void testCreateDocumentWithOptions() throws IOException, APIException, MissingAccessTokenException {
        String[] documentMimeTypes = this.toArray(this.config.getProperty("document.mime.types"));
        Map<String, Object> options = new HashMap<String, Object>();
        List<JSONObject> fieldList = Arrays.asList(
            this.createField("total_amount", "123.00"),
            this.createField("purchase_date", "2019-05-23")
        );
        JSONArray fields = new JSONArray(fieldList);
        options.put("feedback", fields);

        for (String documentMimeType : documentMimeTypes) {
            byte[] content = UUID.randomUUID().toString().getBytes();
            ContentType contentType = ContentType.fromString(documentMimeType);
            String consentId = UUID.randomUUID().toString();

            JSONObject document = this.client.createDocument(content, contentType, consentId, options);
            Assert.assertTrue(document.has("consentId"));
            Assert.assertTrue(document.has("contentType"));
            Assert.assertTrue(document.has("documentId"));
            Assert.assertTrue(document.has("feedback"));
            Assert.assertTrue(document.has("batchId"));
        }
    }

    @Test
    public void testCreatePrediction() throws IOException, APIException, MissingAccessTokenException {
        String[] modelNames = this.toArray(this.config.getProperty("model.names"));
        String[] documentPaths = this.toArray(this.config.getProperty("document.paths"));
        String[] mimeTypes = this.toArray(this.config.getProperty("document.mime.types"));

        for (int i = 0; i < documentPaths.length; ++i) {
            String modelName = modelNames[i];
            ContentType contentType = ContentType.fromString(mimeTypes[i]);
            JSONObject document = this.client.createDocument(this.content, contentType, this.consentId);
            JSONObject prediction = this.client.createPrediction(document.getString("documentId"), modelName);
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
            JSONObject feedback = new JSONObject();

            List<JSONObject> fieldList = Arrays.asList(
                this.createField("total_amount", "123.00"),
                this.createField("purchase_date", "2019-05-23")
            );
            JSONArray fields = new JSONArray(fieldList);
            feedback.put("feedback", fields);

            JSONObject feedbackResponse = this.client.updateDocument(document.getString("documentId"), feedback);

            Assert.assertNotNull(feedbackResponse.get("documentId"));
            Assert.assertNotNull(feedbackResponse.get("consentId"));
            JSONArray feedbackValue = feedbackResponse.getJSONArray("feedback");
            Assert.assertNotNull(feedbackValue);

            StreamSupport.stream(feedbackValue.spliterator(), false)
                .map(o -> (JSONObject)o)
                .forEach(feedbackItem -> {
                    Assert.assertNotNull(feedbackItem.get("label"));
                    Assert.assertNotNull(feedbackItem.get("value"));
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

    @Ignore
    @Test
    public void testGetUser() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.getUser(this.userId);
    }

    @Ignore // DELETE requests are not supported yet
    @Test
    public void testDeleteConsentId() throws IOException, APIException, MissingAccessTokenException {
        String[] documentMimeTypes = this.toArray(this.config.getProperty("document.mime.types"));

        for (String documentMimeType : documentMimeTypes) {
            byte[] content = UUID.randomUUID().toString().getBytes();
            ContentType contentType = ContentType.fromString(documentMimeType);
            this.client.createDocument(content, contentType, this.consentId);

            JSONObject response = this.client.deleteConsent(this.consentId);
            Assert.assertNotNull(response.getString("consentId"));
            JSONArray documentIds = response.getJSONArray("documentIds");
            Assert.assertNotNull(documentIds);
        }
    }

    private String getResourcePath(String relativePath) {
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
