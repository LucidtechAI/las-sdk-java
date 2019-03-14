import ai.lucidtech.las.sdk.Client;

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


public class ClientTest {
    private static final String CONFIG_RELATIVE_PATH = "config.properties";

    private Client client;
    private Properties config;

    @Before
    public void setUp() {
        String configPath = this.getResourcePath(ClientTest.CONFIG_RELATIVE_PATH);

        try(FileInputStream input = new FileInputStream(configPath)) {
            this.config = new Properties();
            this.config.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.client = new Client(this.config.getProperty("endpoint"));
    }

    private String getResourcePath(String relativePath) {
        return getClass().getResource(relativePath).getFile();
    }

    private String[] toArray(String s) {
        return Arrays.stream(s.split(",")).map(String::trim).toArray(String[]::new);
    }

    private static void assertField(JSONObject field) {
        Assert.assertNotNull(field.get("label"));
        Assert.assertNotNull(field.get("value"));
        float confidence = field.getFloat("confidence");
        Assert.assertTrue(confidence >= 0.0);
        Assert.assertTrue(confidence <= 1.0);
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
            String consentId = UUID.randomUUID().toString();

            JSONObject postDocumentsResponse = this.client.postDocuments(documentMimeType, consentId);
            URI uploadUri = new URI(postDocumentsResponse.getString("uploadUrl"));
            String documentId = postDocumentsResponse.getString("documentId");

            this.client.putDocument(documentPath, documentMimeType, uploadUri);
            JSONObject prediction = this.client.postPredictions(documentId, modelName);

            JSONArray fields = prediction.getJSONArray("predictions");
            Assert.assertNotNull(fields);
            StreamSupport.stream(fields.spliterator(), false)
                    .map(o -> (JSONObject)o)
                    .forEach(ClientTest::assertField);
        }
    }
}
