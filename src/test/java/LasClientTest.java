import ai.lucidtech.las.sdk.LasClient;
import ai.lucidtech.las.sdk.ContentType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.StreamSupport;


public class LasClientTest {
    private static final String CONFIG_RELATIVE_PATH = "config.properties";

    private LasClient lasClient;
    private Properties config;

    @Before
    public void setUp() {
        String configPath = this.getResourcePath(LasClientTest.CONFIG_RELATIVE_PATH);

        try(FileInputStream input = new FileInputStream(configPath)) {
            this.config = new Properties();
            this.config.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.lasClient = new LasClient(this.config.getProperty("endpoint"));
    }

    private String getResourcePath(String relativePath) {
        return getClass().getResource(relativePath).getFile();
    }

    private String[] toArray(String s) {
        return Arrays.stream(s.split(",")).map(String::trim).toArray(String[]::new);
    }

    private static void assertDocument(JSONObject document) {
        Assert.assertNotNull(document.get("uploadUrl"));
        Assert.assertNotNull(document.get("documentId"));
    }

    private static void assertField(JSONObject field) {
        Assert.assertNotNull(field.get("label"));
        Assert.assertNotNull(field.get("value"));
        float confidence = field.getFloat("confidence");
        Assert.assertTrue(confidence >= 0.0);
        Assert.assertTrue(confidence <= 1.0);
    }

    @Test
    public void testPostDocuments() throws IOException {
        String[] documentMimeTypes = this.toArray(this.config.getProperty("document.mime.types"));

        for (String documentMimeType : documentMimeTypes) {
            ContentType contentType = ContentType.fromString(documentMimeType);
            String consentId = UUID.randomUUID().toString();

            JSONObject document = this.lasClient.postDocuments(contentType, consentId);
            LasClientTest.assertDocument(document);
        }
    }

    @Test
    public void testPostPredictions() throws IOException, URISyntaxException {
        String[] modelNames = this.toArray(this.config.getProperty("model.names"));
        String[] documentPaths = this.toArray(this.config.getProperty("document.paths"));
        String[] documentMimeTypes = this.toArray(this.config.getProperty("document.mime.types"));
        Assert.assertEquals(modelNames.length, documentPaths.length);
        Assert.assertEquals(modelNames.length, documentMimeTypes.length);

        for (int i = 0; i < documentPaths.length; ++i) {
            String modelName = modelNames[i];
            String documentPath = this.getResourcePath(documentPaths[i]);
            String documentMimeType = documentMimeTypes[i];
            ContentType contentType = ContentType.fromString(documentMimeType);
            String consentId = UUID.randomUUID().toString();

            JSONObject document = this.lasClient.postDocuments(contentType, consentId);
            URI uploadUri = new URI(document.getString("uploadUrl"));
            String documentId = document.getString("documentId");

            this.lasClient.putDocument(documentPath, contentType, uploadUri);
            JSONObject prediction = this.lasClient.postPredictions(documentId, modelName);

            JSONArray fields = prediction.getJSONArray("predictions");
            Assert.assertNotNull(fields);
            StreamSupport.stream(fields.spliterator(), false)
                    .map(o -> (JSONObject)o)
                    .forEach(LasClientTest::assertField);
        }
    }
}
