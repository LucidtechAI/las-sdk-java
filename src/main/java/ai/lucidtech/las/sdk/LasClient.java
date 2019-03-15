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
import java.util.Map;


public class LasClient {
    private String endpoint;
    private Authorization auth;
    private HttpClient httpClient;

    /**
     * A low level client to invoke api methods from Lucidtech AI Services.
     *
     * @param endpoint Domain endpoint of the api, e.g. https://<prefix>.api.lucidtech.ai/<version>
     */
    public LasClient(String endpoint) {
        this.endpoint = endpoint;
        Credentials credentials = new Credentials();
        this.auth = new Authorization(credentials);
        this.httpClient = HttpClientBuilder.create().build();
    }

    /**
     * A low level client to invoke api methods from Lucidtech AI Services.
     *
     * @param endpoint Domain endpoint of the api, e.g. https://<prefix>.api.lucidtech.ai/<version>
     * @param credentials Credentials to use
     * @see Credentials
     */
    public LasClient(String endpoint, Credentials credentials) {
        this.endpoint = endpoint;
        this.auth = new Authorization(credentials);
        this.httpClient = HttpClientBuilder.create().build();
    }

    /**
     * Creates a document handle, calls POST /documents endpoint
     *
     * @param contentType A mime type for the document handle
     * @see ContentType
     * @param consentId An identifier to mark the owner of the document handle
     * @return Response from API
     */
    public JSONObject postDocuments(ContentType contentType, String consentId) throws IOException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("contentType", contentType.getMimeType());
        jsonBody.put("consentId", consentId);

        HttpUriRequest request = this.createSignedRequest("POST", "/documents", jsonBody);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Convenience method for putting a document to presigned url
     *
     * @param documentPath Path to document to upload
     * @param contentType Mime type of document to upload. Same as provided to postDocuments
     * @see ContentType
     * @see LasClient#postDocuments
     * @param presignedUrl Presigned upload url from postDocuments
     * @see LasClient#postDocuments
     * @return Response from PUT operation
     */
    public String putDocument(String documentPath, ContentType contentType, URI presignedUrl) throws IOException {
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
     * @param documentId The document id to run inference and create a prediction. See postDocuments for how to get documentId
     * @see LasClient#postDocuments
     * @param modelName The name of the model to use for inference
     * @return Prediction on document
     */
    public JSONObject postPredictions(String documentId, String modelName) throws IOException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("documentId", documentId);
        jsonBody.put("modelName", modelName);

        HttpUriRequest request = this.createSignedRequest("POST", "/predictions", jsonBody);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Post feedback to the REST API, calls the POST /documents/{documentId} endpoint.
     * Posting feedback means posting the ground truth data for the particular document.
     * This enables the API to learn from past mistakes
     *
     * @param documentId The document id to post feedback to.
     * @see LasClient#postDocuments
     * @param feedback Feedback to post
     * @return Feedback response
     */
    public JSONObject postDocumentId(String documentId, JSONObject feedback) throws IOException {
        HttpUriRequest request = this.createSignedRequest("POST", "/documents/" + documentId, feedback);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    private String executeRequest(HttpUriRequest request) throws IOException {
        HttpResponse httpResponse= this.httpClient.execute(request);
        HttpEntity responseEntity = httpResponse.getEntity();
        return EntityUtils.toString(responseEntity);
    }

    private byte[] readDocument(String documentPath) throws IOException {
        File file = new File(documentPath);
        return Files.readAllBytes(file.toPath());
    }

    private URI createUri(String path) {
        URI uri;

        try {
            uri = new URI(this.endpoint + path);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

        return uri;
    }

    private Map<String, String> createSigningHeaders(URI uri, String method, byte[] body) {
        Map<String, String> headers = this.auth.signHeaders(uri, method, body);
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private HttpUriRequest createSignedRequest(String method, String path, JSONObject jsonBody) {
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

        Map<String, String> headers = this.createSigningHeaders(uri, method, body);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            request.addHeader(header.getKey(), header.getValue());
        }

        return request;
    }
}
