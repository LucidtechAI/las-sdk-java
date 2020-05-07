package ai.lucidtech.las.sdk;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.*;


public class Client {
    private static List<String> VALID_CONTENT_TYPES = Arrays.asList("image/jpeg", "application/pdf");

    private HttpClient httpClient;
    private Credentials credentials;

    /**
     * A low level client to invoke api methods from Lucidtech AI Services.
     */
    public Client() {
        this.credentials = new Credentials();
        this.httpClient = HttpClientBuilder.create().build();
    }

    /**
     * A low level client to invoke api methods from Lucidtech AI Services.
     *
     * @param credentials Credentials to use
     * @see Credentials
     */
    public Client(Credentials credentials) {
        this.credentials = credentials;
        this.httpClient = HttpClientBuilder.create().build();
    }

    public JSONObject getDocument(String documentId) throws IOException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/documents/" + documentId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Creates a document handle, calls POST /documents endpoint
     *
     * @param contentType A mime type for the document handle
     * @see ContentType
     * @param consentId An identifier to mark the owner of the document handle
     * @param options Additional options to include in request body
     * @return Response from API
     */
    public JSONObject createDocument(
        byte[] content,
        ContentType contentType,
        String consentId,
        Map<String, Object> options
    ) throws IOException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("content", Base64.getEncoder().encodeToString(content));
        jsonBody.put("contentType", contentType.getMimeType());
        jsonBody.put("consentId", consentId);

        for (Map.Entry<String, Object> option: options.entrySet()) {
            jsonBody.put(option.getKey(), option.getValue());
        }

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/documents", jsonBody);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Creates a document handle, calls POST /documents endpoint
     *
     * @param contentType A mime type for the document handle
     * @see ContentType
     * @param consentId An identifier to mark the owner of the document handle
     * @return Response from API
     */
    public JSONObject createDocument(
            byte[] content,
            ContentType contentType,
            String consentId
    ) throws IOException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("content", Base64.getEncoder().encodeToString(content));
        jsonBody.put("contentType", contentType.getMimeType());
        jsonBody.put("consentId", consentId);

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/documents", jsonBody);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Convenience method for putting a document to presigned url
     *
     * @param documentPath Path to document to upload
     * @param contentType Mime type of document to upload. Same as provided to createDocument
     * @see ContentType
     * @see Client#createDocument
     * @param presignedUrl Presigned upload url from createDocument
     * @see Client#createDocument
     * @return Response from PUT operation
     */
    public String updateDocument(String documentPath, ContentType contentType, URI presignedUrl) throws IOException {
        byte[] body = this.readDocument(documentPath);

        HttpPut putRequest = new HttpPut(presignedUrl);
        putRequest.addHeader("Content-Type", contentType.getMimeType());
        ByteArrayEntity entity = new ByteArrayEntity(body);
        putRequest.setEntity(entity);

        return this.executeRequest(putRequest);
    }

    /**
     * Run inference and create a prediction, calls the POST /predictions endpoint
     *
     * @param documentId The document id to run inference and create a prediction. See createDocument for how to get documentId
     * @see Client#createDocument
     * @param modelName The name of the model to use for inference
     * @return Prediction on document
     */
    public JSONObject createPrediction(String documentId, String modelName) throws IOException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("documentId", documentId);
        jsonBody.put("modelName", modelName);

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/predictions", jsonBody);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }
//
//    public Prediction predict(String documentPath, String modelName, String consentId) throws IOException, URISyntaxException {
//        byte[] documentContent = Files.readAllBytes(Paths.get(documentPath));
//        ContentType contentType = this.getContentType(documentPath);
//        JSONObject document = this.createDocument(documentContent, contentType, consentId);
//
//        URI uploadUri = new URI(document.getString("uploadUrl"));
//        String documentId = document.getString("documentId");
//        this.putDocument(documentPath, contentType, uploadUri);
//
//        JSONObject prediction = this.postPredictions(documentId, modelName);
//        return new Prediction(documentId, consentId, modelName, prediction);
//    }
//
    /**
     * Post feedback to the REST API, calls the POST /documents/{documentId} endpoint.
     * Posting feedback means posting the ground truth data for the particular document.
     * This enables the API to learn from past mistakes
     *
     * @param documentId The document id to post feedback to.
     * @see Client#createDocument
     * @param feedback Feedback to post
     * @return Feedback response
     */
    public JSONObject updateDocument(String documentId, JSONObject feedback) throws IOException {
        HttpUriRequest request = this.createAuthorizedRequest("POST", "/documents/" + documentId, feedback);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Delete documents with this consent_id, calls the DELETE /consent/{consentId} endpoint.
     *
     * @param consentId Delete documents with this consentId
     * @return Feedback response
     * @see Client#createDocument
     */
    public JSONObject deleteConsent(String consentId) throws IOException {
        HttpUriRequest request = this.createAuthorizedRequest(
            "DELETE",
            "/consents/" + consentId,
            new JSONObject("{}")
        );
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    private String executeRequest(HttpUriRequest request) throws IOException {
        HttpResponse httpResponse= this.httpClient.execute(request);
        HttpEntity responseEntity = httpResponse.getEntity();
        int status = httpResponse.getStatusLine().getStatusCode();

        return EntityUtils.toString(responseEntity);
    }

    private byte[] readDocument(String documentPath) throws IOException {
        File file = new File(documentPath);
        return Files.readAllBytes(file.toPath());
    }

    private URI createUri(String path) {
        URI uri;
        String apiEndpoint = this.credentials.getApiEndpoint();

        try {
            uri = new URI(apiEndpoint + path);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

        return uri;
    }

    private ContentType getContentType(String documentPath) throws IOException {
        File file = new File(documentPath);
        String contentType = Files.probeContentType(file.toPath());
        if (VALID_CONTENT_TYPES.contains(contentType)) {
            return ContentType.fromString(contentType);
        }

        throw new RuntimeException("ContentType not supported: " + contentType);
    }

    private HttpUriRequest createAuthorizedRequest(String method, String path) {
        URI uri = this.createUri(path);
        HttpUriRequest request;

        switch (method) {
            case "GET": {
                request = new HttpGet(uri);
            }
            break;
            case "DELETE": {
                request = new HttpDelete(uri);
            }
            default: throw new IllegalArgumentException("HTTP verb not supported: " + method);
        }

        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer " + this.credentials.getAccessToken(this.httpClient));
        request.addHeader("X-Api-Key", this.credentials.getApiKey());

        return request;
    }

    private HttpUriRequest createAuthorizedRequest(String method, String path, JSONObject jsonBody) {
        URI uri = this.createUri(path);
        HttpUriRequest request;
        byte[] body = null;

        switch (method) {
            case "GET": {
                request = new HttpGet(uri);
            } break;
            case "DELETE": {
                request = new HttpDelete(uri);
            } break;
            case "POST": {
                request = new HttpPost(uri);

                body = jsonBody.toString().getBytes();
                ByteArrayEntity entity = new ByteArrayEntity(body);
                ((HttpPost) request).setEntity(entity);
            } break;
            default: throw new IllegalArgumentException("HTTP verb not supported: " + method);
        }

        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer " + this.credentials.getAccessToken(this.httpClient));
        request.addHeader("X-Api-Key", this.credentials.getApiKey());

        return request;
    }
}
