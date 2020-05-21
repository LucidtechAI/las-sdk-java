package ai.lucidtech.las.sdk;

import org.apache.http.StatusLine;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Client {
    private static List<String> VALID_CONTENT_TYPES = Arrays.asList("image/jpeg", "application/pdf");

    private HttpClient httpClient;
    private Credentials credentials;

    /**
     * A client to invoke api methods from Lucidtech AI Services.
     *
     * @param credentials Credentials to use
     * @see Credentials
     */
    public Client(Credentials credentials) {
        this.credentials = credentials;
        this.httpClient = HttpClientBuilder.create().build();
    }

    /**
     *
     * @param documentId The document id to run inference and create a prediction on
     * @return response from the API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject getDocument(String documentId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/documents/" + documentId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *
     * @return All documents from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject listDocuments() throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/documents");
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *
     * @param options Available options are:
     * batchId - the batch id that contains the documents of interest
     * consentId - an identifier to mark the owner of the document handle
     * @return documents from REST API filtered using the passed options
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject listDocuments(
        List<NameValuePair> options
    ) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/documents", options);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Creates a document handle, calls POST /documents endpoint
     *
     * @see ContentType
     * @param content Binary data
     * @param contentType A mime type for the document handle
     * @param consentId An identifier to mark the owner of the document handle
     * @param options Additional options to include in request body
     * @return Response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject createDocument(
        byte[] content,
        ContentType contentType,
        String consentId,
        Map<String, Object> options
    ) throws IOException, APIException, MissingAccessTokenException {
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
     * @see ContentType
     * @param content Binary data
     * @param contentType A mime type for the document handle
     * @param consentId An identifier to mark the owner of the document handle
     * @return Response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject createDocument(
        byte[] content,
        ContentType contentType,
        String consentId
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("content", Base64.getEncoder().encodeToString(content));
        jsonBody.put("contentType", contentType.getMimeType());
        jsonBody.put("consentId", consentId);

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/documents", jsonBody);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Run inference and create a prediction, calls the POST /predictions endpoint
     *
     * @see Client#createDocument
     * @param documentId The document id to run inference and create a prediction. See createDocument for how to get documentId
     * @param modelName The name of the model to use for inference
     * @return Prediction on document
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject createPrediction(
        String documentId,
        String modelName
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("documentId", documentId);
        jsonBody.put("modelName", modelName);

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/predictions", jsonBody);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Run inference and create a prediction, calls the POST /predictions endpoint
     *
     * @see Client#createDocument
     * @param documentId The document id to run inference and create a prediction. See createDocument for how to get documentId
     * @param modelName The name of the model to use for inference
     * @param options Available options are:
     *   maxPages - maximum number of pages to run predictions on
     *   autoRotate - whether or not to let the API try different rotations on
     * @return Prediction on document
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject createPrediction(
        String documentId,
        String modelName,
        Map<String, Object> options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("documentId", documentId);
        jsonBody.put("modelName", modelName);

        for (Map.Entry<String, Object> option: options.entrySet()) {
            jsonBody.put(option.getKey(), option.getValue());
        }

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/predictions", jsonBody);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Create a prediction on a document <i>documentPath</i> by path using model <i>modelName</i>.
     * This method takes care of creating and uploading a document as well as running inference using
     * model to create prediction on the document.
     *
     * @param documentPath Path to document to run inference on
     * @param modelName The name of the model to use for inference
     * @param consentId An identifier to mark the owner of the document handle
     * @return Prediction on document
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public Prediction predict(
        String documentPath,
        String modelName,
        String consentId
    ) throws IOException, APIException, MissingAccessTokenException {
        byte[] documentContent = Files.readAllBytes(Paths.get(documentPath));
        ContentType contentType = this.getContentType(documentPath);
        JSONObject document = this.createDocument(documentContent, contentType, consentId);
        String documentId = document.getString("documentId");

        JSONObject prediction = this.createPrediction(documentId, modelName);
        return new Prediction(documentId, consentId, modelName, prediction);
    }

    /**
     * Post feedback to the REST API, calls the POST /documents/{documentId} endpoint.
     * Posting feedback means posting the ground truth data for the particular document.
     * This enables the API to learn from past mistakes
     *
     * @see Client#createDocument
     * @param documentId The document id to post feedback to.
     * @param feedback Feedback to post
     * @return Feedback response
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject updateDocument(
        String documentId,
        JSONObject feedback
    ) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("POST", "/documents/" + documentId, feedback);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Creates a batch handle, calls the POST /batches endpoint
     * @param description Creates a batch handle, calls the POST /batches endpoint
     * @return Batch handle id and pre-signed upload url
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject createBatch(String description) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();
        body.put("description", description);
        HttpUriRequest request = this.createAuthorizedRequest("POST", "/batches", body);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Delete documents with this consent_id, calls the DELETE /consent/{consentId} endpoint.
     *
     * @see Client#createDocument
     * @param consentId Delete documents with this consentId
     * @return Feedback response
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject deleteConsent(String consentId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest(
            "DELETE",
            "/consents/" + consentId,
            new JSONObject("{}")
        );
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Get information about user, calls the GET /users/{user_id} endpoint.
     * @param userId The user_id to get consent hash for
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     */
    public JSONObject getUser(String userId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/users/" + userId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    private String executeRequest(HttpUriRequest request) throws IOException, APIException {
        HttpResponse httpResponse= this.httpClient.execute(request);
        HttpEntity responseEntity = httpResponse.getEntity();
        StatusLine statusLine = httpResponse.getStatusLine();
        int status = statusLine.getStatusCode();

        if (status == HttpStatus.SC_FORBIDDEN) {
            throw new APIException("Credentials provided are not valid");
        }

        if (status == 429) {
            throw new APIException("You have reached the limit of requests per second");
        }

        if (status > 299) {
            throw new APIException(status, statusLine.getReasonPhrase());
        }

        return EntityUtils.toString(responseEntity);
    }

    private byte[] readDocument(String documentPath) throws IOException {
        File file = new File(documentPath);
        return Files.readAllBytes(file.toPath());
    }

    private URI createUri(String path) throws URISyntaxException {
        String apiEndpoint = this.credentials.getApiEndpoint();
        return new URI(apiEndpoint + path);
    }

    private URI createUri(String path, List<NameValuePair> queryParams) throws URISyntaxException {
        URI uri;
        String apiEndpoint = this.credentials.getApiEndpoint();

        uri = new URI(apiEndpoint + path);

        URIBuilder builder = new URIBuilder(uri);
        builder.addParameters(queryParams);

        return builder.build();
    }

    private ContentType getContentType(String documentPath) throws IOException {
        File file = new File(documentPath);
        String contentType = Files.probeContentType(file.toPath());
        if (VALID_CONTENT_TYPES.contains(contentType)) {
            return ContentType.fromString(contentType);
        }

        throw new RuntimeException("ContentType not supported: " + contentType);
    }

    private HttpUriRequest createAuthorizedRequest(String method, String path) throws MissingAccessTokenException {
        URI uri;

        try {
            uri = this.createUri(path);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to create url");
        }

        HttpUriRequest request;

        switch (method) {
            case "GET": {
                request = new HttpGet(uri);
            } break;
            case "DELETE": {
                request = new HttpDelete(uri);
            } break;
            default: throw new IllegalArgumentException("HTTP verb not supported: " + method);
        }

        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer " + this.credentials.getAccessToken(this.httpClient));
        request.addHeader("X-Api-Key", this.credentials.getApiKey());

        return request;
    }

    private HttpUriRequest createAuthorizedRequest(
        String method,
        String path,
        List<NameValuePair> queryParams
    ) throws MissingAccessTokenException {
        URI uri;

        try {
            uri = this.createUri(path, queryParams);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to create url");
        }

        HttpUriRequest request;

        switch (method) {
            case "GET": {
                request = new HttpGet(uri);
            } break;
            case "DELETE": {
                request = new HttpDelete(uri);
            } break;
            default: throw new IllegalArgumentException("HTTP verb not supported: " + method);
        }

        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer " + this.credentials.getAccessToken(this.httpClient));
        request.addHeader("X-Api-Key", this.credentials.getApiKey());

        return request;
    }

    private HttpUriRequest createAuthorizedRequest(
        String method,
        String path,
        JSONObject jsonBody
    ) throws MissingAccessTokenException {
        URI uri;

        try {
            uri = this.createUri(path);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to create url");
        }

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
