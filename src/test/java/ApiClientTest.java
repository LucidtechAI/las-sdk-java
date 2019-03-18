import ai.lucidtech.las.sdk.ApiClient;
import ai.lucidtech.las.sdk.Field;
import ai.lucidtech.las.sdk.Prediction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


public class ApiClientTest {
    private static final String CONFIG_RELATIVE_PATH = "config.properties";

    private ApiClient client;
    private Properties config;

    @Before
    public void setUp() {
        String configPath = this.getResourcePath(ApiClientTest.CONFIG_RELATIVE_PATH);

        try(FileInputStream input = new FileInputStream(configPath)) {
            this.config = new Properties();
            this.config.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.client = new ApiClient(this.config.getProperty("endpoint"));
    }

    private String getResourcePath(String relativePath) {
        return getClass().getResource(relativePath).getFile();
    }

    private String[] toArray(String s) {
        return Arrays.stream(s.split(",")).map(String::trim).toArray(String[]::new);
    }

    private static void assertFields(List<Field> fields) {
        for (Field field : fields) {
            Assert.assertNotNull(field.getLabel());
            Assert.assertNotNull(field.getValue());
            Float confidence = field.getConfidence();
            Assert.assertNotNull(confidence);
            Assert.assertTrue(confidence >= 0.0);
            Assert.assertTrue(confidence <= 1.0);
        }
    }

    @Test
    public void testPredict() throws IOException, URISyntaxException {
        String[] modelNames = this.toArray(this.config.getProperty("model.names"));
        String[] documentPaths = this.toArray(this.config.getProperty("document.paths"));
        Assert.assertEquals(modelNames.length, documentPaths.length);

        for (int i = 0; i < documentPaths.length; ++i) {
            String modelName = modelNames[i];
            String documentPath = this.getResourcePath(documentPaths[i]);
            String consentId = UUID.randomUUID().toString();

            Prediction prediction = this.client.predict(documentPath, modelName, consentId);
            Assert.assertNotNull(prediction.getDocumentId());
            Assert.assertNotNull(prediction.getConsentId());
            Assert.assertNotNull(prediction.getModelName());
            List<Field> fields = prediction.getFields();
            Assert.assertNotNull(fields);
            ApiClientTest.assertFields(fields);
        }
    }
}
