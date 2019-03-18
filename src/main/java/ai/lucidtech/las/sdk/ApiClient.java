package ai.lucidtech.las.sdk;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;


public class ApiClient extends Client {
    private static List<String> VALID_CONTENT_TYPES = Arrays.asList("image/jpeg", "application/pdf");

    public ApiClient(String endpoint) {
        super(endpoint);
    }

    public ApiClient(String endpoint, Credentials credentials) {
        super(endpoint, credentials);
    }

    public Prediction predict(String documentPath, String modelName, String consentId) throws IOException, URISyntaxException {
        ContentType contentType = this.getContentType(documentPath);
        JSONObject document = this.postDocuments(contentType, consentId);

        URI uploadUri = new URI(document.getString("uploadUrl"));
        String documentId = document.getString("documentId");
        this.putDocument(documentPath, contentType, uploadUri);

        JSONObject prediction = this.postPredictions(documentId, modelName);
        return new Prediction(documentId, consentId, modelName, prediction);
    }

    private ContentType getContentType(String documentPath) throws IOException {
        File file = new File(documentPath);
        String contentType = Files.probeContentType(file.toPath());
        if (VALID_CONTENT_TYPES.contains(contentType)) {
            return ContentType.fromString(contentType);
        }

        throw new RuntimeException("ContentType not supported: " + contentType);
    }
}
