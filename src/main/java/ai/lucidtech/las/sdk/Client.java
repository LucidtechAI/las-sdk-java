package ai.lucidtech.las.sdk;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONObject;


public class Client {

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
     * Creates an app client, calls the POST /appClients endpoint.

     * @see CreateAppClientOptions
     * @param options Additional options to include in request body
     * @return Asset response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createAppClient(
        CreateAppClientOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();

        if (options != null) {
            body = options.addOptions(body);
        }

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/appClients", body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Creates an app client, calls the POST /appClients endpoint.

     * @return Asset response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createAppClient() throws IOException, APIException, MissingAccessTokenException {
        return this.createAppClient(null);
    }

    /**
     *  List appClients available, calls the GET /appClients endpoint.
     *
     * @see ListAppClientsOptions
     * @param options Additional options to pass along as query parameters
     * @return AppClients response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listAppClients(
        ListAppClientsOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/appClients", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List appClients available, calls the GET /appClients endpoint.
     *
     * @return AppClients response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listAppClients() throws IOException, APIException, MissingAccessTokenException {
        return this.listAppClients(null);
    }

    /**
     * Delete an appClient, calls the DELETE /appClients/{appClientId} endpoint.
     *
     * @param appClientId Id of the appClient
     * @return AppClient response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteAppClient(
        String appClientId
    ) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("DELETE", "/appClients/" + appClientId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Creates an asset, calls the POST /assets endpoint.

     * @see CreateAssetOptions
     * @param content Binary data
     * @param options Additional options to include in request body
     * @return Asset response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createAsset(
        byte[] content,
        CreateAssetOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();
        body.put("content", Base64.getEncoder().encodeToString(content));

        if (options != null) {
            body = options.addOptions(body);
        }

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/assets", body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Creates an asset, calls the POST /assets endpoint.

     * @see CreateAssetOptions
     * @param content Data from input stream
     * @param options Additional options to include in request body
     * @return Asset response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createAsset(
        InputStream content,
        CreateAssetOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.createAsset(IOUtils.toByteArray(content), options);
    }

    /**
     * Creates an asset, calls the POST /assets endpoint.

     * @param content Binary data
     * @return Asset response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createAsset(byte[] content) throws IOException, APIException, MissingAccessTokenException {
        return this.createAsset(content, null);
    }

    /**
     * Creates an asset, calls the POST /assets endpoint.

     * @param content Data from input stream
     * @return Asset response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createAsset(InputStream content) throws IOException, APIException, MissingAccessTokenException {
        return this.createAsset(IOUtils.toByteArray(content), null);
    }

    /**
     *  List assets available, calls the GET /assets endpoint.
     *
     * @see ListAssetsOptions
     * @param options Additional options to pass along as query parameters
     * @return Assets response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listAssets(ListAssetsOptions options)
    throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/assets", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List assets available, calls the GET /assets endpoint.
     *
     * @return Assets response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listAssets() throws IOException, APIException, MissingAccessTokenException {
        return this.listAssets(null);
    }

    /**
     *  Get asset, calls the GET /assets/{assetId} endpoint.
     *
     * @param assetId Id of the asset
     * @return Asset response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject getAsset(String assetId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/assets/" + assetId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Updates an asset, calls the PATCH /assets/{assetId} endpoint.
     *
     * @see UpdateAssetOptions
     * @param assetId Id of the asset
     * @param options Additional options to include in request body
     * @return Asset response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject updateAsset(
        String assetId,
        UpdateAssetOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();

        if (options != null) {
            body = options.addOptions(body);
        }

        HttpUriRequest request = this.createAuthorizedRequest("PATCH", "/assets/" + assetId, body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Delete an asset, calls the DELETE /assets/{assetId} endpoint.
     *
     * @param assetId Id of the asset
     * @return Asset response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteAsset(
        String assetId
    ) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("DELETE", "/assets/" + assetId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Creates a batch, calls the POST /batches endpoint.
     *
     * @see CreateBatchOptions
     * @param options Additional options to include in request body
     * @return Batch response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createBatch(CreateBatchOptions options)
    throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();

        if (options != null){
            body = options.addOptions(body);
        }

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/batches", body);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Creates a batch, calls the POST /batches endpoint.
     *
     * @return Batch response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createBatch() throws IOException, APIException, MissingAccessTokenException {
        return this.createBatch(null);
    }

    /**
     *  List batches available, calls the GET /batches endpoint.
     *
     * @see ListBatchesOptions
     * @param options Additional options to pass along as query parameters
     * @return Batches response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listBatches(ListBatchesOptions options)
    throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/batches", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List batches available, calls the GET /batches endpoint.
     *
     * @return Batches response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listBatches() throws IOException, APIException, MissingAccessTokenException {
        return this.listBatches(null);
    }

    /**
     * Delete an batch, calls the DELETE /batches/{batchId} endpoint.
     *
     * @param batchId Id of the batch
     * @param deleteDocuments Set to true to delete documents in batch before deleting batch
     * @return Batch response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteBatch(
        String batchId,
        boolean deleteDocuments
    ) throws IOException, APIException, MissingAccessTokenException {
        if (deleteDocuments) {
            DeleteDocumentsOptions options = new DeleteDocumentsOptions().setBatchId(new String[] {batchId});
            JSONObject documents = this.deleteDocuments(options);
            String nextTokenKey = "nextToken";
            String nextToken = documents.isNull(nextTokenKey) ? null : documents.getString(nextTokenKey);

            while (nextToken != null) {
                options = options.setNextToken(nextToken);
                documents = this.deleteDocuments(options);
                nextToken = !documents.isNull(nextTokenKey) ? documents.getString(nextTokenKey) : null;
            }
        }

        HttpUriRequest request = this.createAuthorizedRequest("DELETE", "/batches/" + batchId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Delete an batch, calls the DELETE /batches/{batchId} endpoint.
     *
     * @param batchId Id of the batch
     * @return Batch response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteBatch(
        String batchId
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.deleteBatch(batchId, false);
    }

    /**
     * Creates a document, calls the POST /documents endpoint.
     *
     * @see CreateDocumentOptions
     * @param content Binary data
     * @param contentType A mime type for the document
     * @param options Additional options to include in request body
     * @return Document response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createDocument(
        byte[] content,
        ContentType contentType,
        CreateDocumentOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();
        body.put("content", Base64.getEncoder().encodeToString(content));
        body.put("contentType", contentType.getMimeType());

        if (options != null) {
            body = options.addOptions(body);
        }

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/documents", body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }


    /**
     * Creates a document, calls the POST /documents endpoint.
     *
     * @see CreateDocumentOptions
     * @param content Data from input stream
     * @param contentType A mime type for the document
     * @param options Additional options to include in request body
     * @return Document response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createDocument(
        InputStream content,
        ContentType contentType,
        CreateDocumentOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        byte[] byteArrayContent = IOUtils.toByteArray(content);
        return this.createDocument(byteArrayContent, contentType, options);
    }

    /**
     * Creates a document, calls the POST /documents endpoint.
     *
     * @see CreateDocumentOptions
     * @param content Data from input stream
     * @param contentType A mime type for the document
     * @return Document response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createDocument(
        InputStream content,
        ContentType contentType
    ) throws IOException, APIException, MissingAccessTokenException {
        byte[] byteArrayContent = IOUtils.toByteArray(content);
        return this.createDocument(byteArrayContent, contentType, null);
    }

    /**
     * Creates a document, calls the POST /documents endpoint.
     *
     * @see CreateDocumentOptions
     * @param content Binary data
     * @param contentType A mime type for the document
     * @return Document response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createDocument(
        byte[] content,
        ContentType contentType
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.createDocument(content, contentType, null);
    }

    /**
     *  List documents, calls the GET /documents endpoint.
     *
     * @see ListDocumentsOptions
     * @param options Additional options to pass along as query parameters
     * @return Documents response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listDocuments(ListDocumentsOptions options)
        throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/documents", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List documents, calls the GET /documents endpoint.
     *
     * @return Documents response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listDocuments() throws IOException, APIException, MissingAccessTokenException {
        return this.listDocuments(null);
    }

    /**
     *  Delete documents, calls the DELETE /documents endpoint.
     *
     * @see DeleteDocumentsOptions
     * @param options Additional options to pass along as query parameters
     * @return Documents response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteDocuments(
        DeleteDocumentsOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("DELETE", "/documents", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Delete documents, calls the DELETE /documents endpoint.
     *
     * @see Client#createDocument
     * @return Documents response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteDocuments() throws IOException, APIException, MissingAccessTokenException {
        return this.deleteDocuments(null);
    }

    /**
     *  Get document, calls the GET /documents/{documentId} endpoint.
     *
     * @param documentId Id of the document
     * @return Document response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject getDocument(String documentId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/documents/" + documentId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Update ground truth for a document, calls the PATCH /documents/{documentId} endpoint.
     *
     * @see Client#createDocument
     * @param documentId The document id to post groundTruth to.
     * @param groundTruth List of json objects containing label and value for the ground truth
     * @return Document response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject updateDocument(
        String documentId,
        JSONArray groundTruth
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();
        body.put("groundTruth", groundTruth);
        HttpUriRequest request = this.createAuthorizedRequest("PATCH", "/documents/" + documentId, body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     *  Get log, calls the GET /logs/{logId} endpoint.
     *
     * @param logId Id of the log
     * @return Log response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject getLog(String logId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/logs/" + logId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List logs, calls the GET /logs endpoint.
     *
     * @see ListLogsOptions
     * @param options Additional options to pass along as query parameters
     * @return Logs response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listLogs(
        ListLogsOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/logs", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List logs, calls the GET /logs endpoint.
     *
     * @return Logs response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listLogs() throws IOException, APIException, MissingAccessTokenException {
        return this.listLogs(null);
    }

    /**
     *  List models available, calls the GET /models endpoint.
     *
     * @see ListModelsOptions
     * @param options Additional options to pass along as query parameters
     * @return Models response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listModels(
        ListModelsOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/models", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List models available, calls the GET /models endpoint.
     *
     * @return Models response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listModels() throws IOException, APIException, MissingAccessTokenException {
        return listModels(null);
    }

    /**
     * Create a prediction on a document using specified model, calls the POST /predictions endpoint.
     *
     * @see Client#createDocument
     * @see CreatePredictionOptions
     * @param documentId The document id to run inference and create a prediction on.
     * @param modelId The id of the model to use for inference
     * @param options Additional options to include in request body
     * @return Prediction response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createPrediction(
        String documentId,
        String modelId,
        CreatePredictionOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();
        body.put("documentId", documentId);
        body.put("modelId", modelId);
        if (options != null) {
            body = options.addOptions(body);
        }
        HttpUriRequest request = this.createAuthorizedRequest("POST", "/predictions", body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Create a prediction on a document using specified model, calls the POST /predictions endpoint.
     *
     * @see Client#createDocument
     * @param documentId The document id to run inference and create a prediction on.
     * @param modelId The id of the model to use for inference
     * @return Prediction response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createPrediction(
        String documentId,
        String modelId
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.createPrediction(documentId, modelId, null);
    }

    /**
     *  List predictions available, calls the GET /predictions endpoint.
     *
     * @see ListPredictionsOptions
     * @param options Additional options to pass along as query parameters
     * @return Predictions response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listPredictions(
        ListPredictionsOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/predictions", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List predictions available, calls the GET /predictions endpoint.
     *
     * @return Predictions response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listPredictions() throws IOException, APIException, MissingAccessTokenException {
        return this.listPredictions(null);
    }

    /**
     * Creates a secret, calls the POST /secrets endpoint.

     * @see CreateSecretOptions
     * @param data Key-Value pairs to store secretly
     * @param options Additional options to include in request body
     * @return Secret response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createSecret(
        JSONObject data,
        CreateSecretOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject(){{ put("data", data); }};
        if (options != null) {
            body = options.addOptions(body);
        }
        HttpUriRequest request = this.createAuthorizedRequest("POST", "/secrets", body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Creates a secret, calls the POST /secrets endpoint.

     * @see CreateSecretOptions
     * @param data Key-Value pairs to store secretly
     * @param options Additional options to include in request body
     * @return Secret response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createSecret(
        Map<String, String> data,
        CreateSecretOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.createSecret(new JSONObject(data), options);
    }

    /**
     * Creates a secret, calls the POST /secrets endpoint.

     * @param data Key-Value pairs to store secretly
     * @return Secret response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createSecret(
        Map<String, String> data
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.createSecret(data, null);
    }

    /**
     * Creates a secret, calls the POST /secrets endpoint.

     * @param data Key-Value pairs to store secretly
     * @return Secret response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createSecret(
        JSONObject data
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.createSecret(data, null);
    }

    /**
     *  List secrets available, calls the GET /secrets endpoint.
     *
     * @see ListSecretsOptions
     * @param options Additional options to pass along as query parameters
     * @return Secrets response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listSecrets(
        ListSecretsOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/secrets", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List secrets available, calls the GET /secrets endpoint.
     *
     * @return Secrets response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listSecrets() throws IOException, APIException, MissingAccessTokenException {
        return this.listSecrets(null);
    }

    /**
     * Updates a secret, calls the PATCH /secrets/{secretId} endpoint.
     *
     * @see UpdateSecretOptions
     * @param secretId Id of the secret
     * @param options Additional options to include in request body
     * @return Secret response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject updateSecret(
        String secretId,
        UpdateSecretOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("PATCH", "/secrets/" + secretId, options.toJson());
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Delete a secret, calls the DELETE /secrets/{secretId} endpoint.
     *
     * @param secretId Id of the secret
     * @return Secret response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteSecret(
        String secretId
    ) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("DELETE", "/secrets/" + secretId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Creates a transition, calls the POST /transitions endpoint.
     *
     * @see CreateTransitionOptions
     * @see TransitionType
     * @param transitionType Type of transition
     * @param options Additional options to include in request body
     * @return Transition response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createTransition(
        TransitionType transitionType,
        CreateTransitionOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();
        body.put("transitionType", transitionType.value);

        if (options != null) {
            body = options.addOptions(body);
        }

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/transitions", body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Creates a transition, calls the POST /transitions endpoint.

     * @see TransitionType
     * @param transitionType Type of transition
     * @return Transition response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createTransition(
        TransitionType transitionType
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.createTransition(transitionType, null);
    }

    /**
     *  List transitions, calls the GET /transitions endpoint.
     *
     * @see ListTransitionsOptions
     * @param options Additional options to pass along as query parameters
     * @return Transitions response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listTransitions(ListTransitionsOptions options)
    throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/transitions", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List transitions, calls the GET /transitions endpoint.
     *
     * @return Transitions response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listTransitions() throws IOException, APIException, MissingAccessTokenException {
        return this.listTransitions(null);
    }

    /**
     *  Get transition, calls the GET /transitions/{transitionId} endpoint.
     *
     * @param transitionId Id of the transition
     * @return Transition response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject getTransition(String transitionId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/transitions/" + transitionId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Updates a transition, calls the PATCH /transitions/{transitionId} endpoint.
     *
     * @see UpdateTransitionOptions
     * @param transitionId Id of the transition
     * @param options Additional options to include in request body
     * @return Transition response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject updateTransition(
        String transitionId,
        UpdateTransitionOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        String path = "/transitions/" + transitionId;
        HttpUriRequest request = this.createAuthorizedRequest("PATCH", path, options.toJson());
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Start executing a manual transition, calls the POST /transitions/{transitionId}/executions endpoint.
     *
     * @param transitionId Id of the transition
     * @return TransitionExecution response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject executeTransition(
        String transitionId
    ) throws IOException, APIException, MissingAccessTokenException {
        String path = "/transitions/" + transitionId + "/executions";
        HttpUriRequest request = this.createAuthorizedRequest("POST", path, new JSONObject());
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Delete a transition, calls the DELETE /transitions/{transitionId} endpoint.
     * Will fail if transition is in use by one or more workflows.
     *
     * @param transitionId Id of the transition
     * @return Transition response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteTransition(
        String transitionId
    ) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("DELETE", "/transitions/" + transitionId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List executions in a transition, calls the GET /transitions/{transitionId}/executions endpoint.
     *
     * @see ListTransitionExecutionsOptions
     * @param transitionId Id of the transition
     * @param options Additional options to pass along as query parameters
     * @return Transition executions response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listTransitionExecutions(String transitionId, ListTransitionExecutionsOptions options)
    throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        String path = "/transitions/" + transitionId + "/executions";
        HttpUriRequest request = this.createAuthorizedRequest("GET", path, queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List executions in a transition, calls the GET /transitions/{transitionId}/executions endpoint.
     *
     * @param transitionId Id of the transition
     * @return Transition executions response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listTransitionExecutions(String transitionId)
    throws IOException, APIException, MissingAccessTokenException {
        return this.listTransitionExecutions(transitionId, null);
    }

    /**
     * Get an execution of a transition, calls the GET /transitions/{transitionId}/executions/{executionId} endpoint
     *
     * @param transitionId Id of the transition
     * @param executionId Id of the execution
     * @return TransitionExecution response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject getTransitionExecution(String transitionId, String executionId)
    throws IOException, APIException, MissingAccessTokenException {
        String path = "/transitions/" + transitionId + "/executions/" + executionId;
        HttpUriRequest request = this.createAuthorizedRequest("GET", path);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Ends the processing of the transition execution,
     * calls the PATCH /transitions/{transitionId}/executions/{executionId} endpoint.
     *
     * @see UpdateTransitionExecutionOptions
     * @see TransitionExecutionStatus
     * @param transitionId Id of the transition
     * @param executionId Id of the execution
     * @param status Status of the execution
     * @param options Additional options to include in request body
     * @return Transition response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject updateTransitionExecution(
        String transitionId,
        String executionId,
        TransitionExecutionStatus status,
        UpdateTransitionExecutionOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();
        body.put("status", status.value);
        body = options.addOptions(body);
        String path = "/transitions/" + transitionId + "/executions/" + executionId;
        HttpUriRequest request = this.createAuthorizedRequest("PATCH", path, body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Send heartbeat for a manual execution to signal that we are still working on it.
     * Must be done at minimum once every 60 seconds or the transition execution will time out,
     * calls the POST /transitions/{transitionId}/executions/{executionId}/heartbeats endpoint.
     *
     * @param transitionId Id of the transition
     * @param executionId Id of the execution
     * @return Empty response
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject sendHeartbeat(
        String transitionId,
        String executionId
    ) throws IOException, APIException, MissingAccessTokenException {
        String path = "/transitions/" + transitionId + "/executions/" + executionId + "/heartbeats";
        HttpUriRequest request = this.createAuthorizedRequest("POST", path, new JSONObject());
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Creates a new user, calls the POST /users endpoint.
     *
     * @see CreateUserOptions
     * @param email Email of the new user
     * @param options Additional options to include in request body
     * @return User response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createUser(
        String email,
        CreateUserOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();
        body.put("email", email);

        if (options != null) {
            body = options.addOptions(body);
        }

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/users", body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Creates a new user, calls the POST /users endpoint.
     *
     * @param email Email to the new user
     * @return User response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createUser(String email) throws IOException, APIException, MissingAccessTokenException {
        return this.createUser(email, null);
    }

    /**
     *  List users, calls the GET /users endpoint.
     *
     * @see ListUsersOptions
     * @param options Additional options to pass along as query parameters
     * @return Users response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listUsers(
        ListUsersOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/users", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List users, calls the GET /users endpoint.
     *
     * @return Users response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listUsers() throws IOException, APIException, MissingAccessTokenException {
        return this.listUsers(null);
    }

    /**
     * Get user, calls the GET /users/{userId} endpoint.
     *
     * @param userId Id of user
     * @return User response
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject getUser(String userId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/users/" + userId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Updates a user, calls the PATCH /users/{userId} endpoint.
     *
     * @see UpdateUserOptions
     * @param userId Id of user
     * @param options Additional options to include in request body
     * @return User response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject updateUser(
        String userId,
        UpdateUserOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("PATCH", "/users/" + userId, options.toJson());
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Deletes a user, calls the PATCH /users/{userId} endpoint.
     *
     * @param userId Id of user
     * @return User response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteUser(String userId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("DELETE", "/users/" + userId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Creates a new workflow, calls the POST /workflows endpoint.
     * Check out Lucidtech's tutorials for more info on how to create a workflow.
     * see https://docs.lucidtech.ai/getting-started/tutorials/setup_predict_and_approve
     *
     * @see CreateWorkflowOptions
     * @param specification Specification of the workflow,
     * currently supporting ASL: https://states-language.net/spec.html. Check out the tutorials for more information:
     * see https://docs.lucidtech.ai/getting-started/tutorials/setup_predict_and_approve#creating-the-workflow
     * @param options Additional options to include in request body
     * @return Workflow response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createWorkflow(
        JSONObject specification,
        CreateWorkflowOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        JSONObject body = new JSONObject();
        body.put("specification", specification);

        if (options != null) {
            body = options.addOptions(body);
        }

        HttpUriRequest request = this.createAuthorizedRequest("POST", "/workflows", body);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Creates a new workflow, calls the POST /workflows endpoint.
     * Check out Lucidtech's tutorials for more info on how to create a workflow.
     * see https://docs.lucidtech.ai/getting-started/tutorials/setup_predict_and_approve
     *
     * @param specification Specification of the workflow,
     * currently supporting ASL: https://states-language.net/spec.html. Check out the tutorials for more information:
     * see https://docs.lucidtech.ai/getting-started/tutorials/setup_predict_and_approve#creating-the-workflow
     * @return Workflow response from API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject createWorkflow(
        JSONObject specification
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.createWorkflow(specification, null);
    }

    /**
     *  List workflows, calls the GET /workflows endpoint.
     *
     * @see ListWorkflowsOptions
     * @param options Additional options to pass along as query parameters
     * @return Workflows response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listWorkflows(
        ListWorkflowsOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/workflows", queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List workflows, calls the GET /workflows endpoint.
     *
     * @return Workflows response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listWorkflows() throws IOException, APIException, MissingAccessTokenException {
        return this.listWorkflows(null);
    }

    /**
     *  Get workflow, calls the GET /workflows/{workflowId} endpoint.
     *
     * @param workflowId Id of the workflow
     * @return Workflow response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject getWorkflow(String workflowId) throws IOException, APIException, MissingAccessTokenException {
        HttpUriRequest request = this.createAuthorizedRequest("GET", "/workflows/" + workflowId);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Updates a workflow, calls the PATCH /workflows/{workflowId} endpoint.
     *
     * @see UpdateWorkflowOptions
     * @param workflowId Id of the workflow
     * @param options Additional options to include in request body
     * @return Workflow response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject updateWorkflow(
        String workflowId,
        UpdateWorkflowOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        String path = "/workflows/" + workflowId;
        HttpUriRequest request = this.createAuthorizedRequest("PATCH", path, options.toJson());
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     * Delete a workflow, calls the DELETE /workflows/{workflowId} endpoint.
     *
     * @see Client#createWorkflow
     * @param workflowId Id of the workflow
     * @return Workflow response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteWorkflow(String workflowId) throws IOException, APIException, MissingAccessTokenException {
        String path = "/workflows/" + workflowId;
        HttpUriRequest request = this.createAuthorizedRequest("DELETE", path);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     * Start a workflow execution, calls the POST /workflows/{workflowId}/executions endpoint.
     *
     * @param workflowId Id of the workflow
     * @param content Input to the first step of the workflow
     * @return WorkflowExecution response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject executeWorkflow(
        String workflowId,
        JSONObject content
    ) throws IOException, APIException, MissingAccessTokenException {
        String path = "/workflows/" + workflowId + "/executions";
        HttpUriRequest request = this.createAuthorizedRequest("POST", path, content);
        String jsonResponse = this.executeRequest(request);
        return new JSONObject(jsonResponse);
    }

    /**
     *  List executions in a workflow, calls the GET /workflows/{workflowId}/executions endpoint.
     *
     * @see ListWorkflowExecutionsOptions
     * @param workflowId Id of the workflow
     * @param options Additional options to pass along as query parameters
     * @return Workflow executions response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listWorkflowExecutions(
        String workflowId,
        ListWorkflowExecutionsOptions options
    ) throws IOException, APIException, MissingAccessTokenException {
        List<NameValuePair> queryParameters = getQueryParameters(options);
        String path = "/workflows/" + workflowId + "/executions";
        HttpUriRequest request = this.createAuthorizedRequest("GET", path, queryParameters);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    /**
     *  List executions in a workflow, calls the GET /workflows/{workflowId}/executions endpoint.
     *
     * @param workflowId Id of the workflow
     * @return Workflow executions response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject listWorkflowExecutions(
        String workflowId
    ) throws IOException, APIException, MissingAccessTokenException {
        return this.listWorkflowExecutions(workflowId, null);
    }

    /**
     * Deletes execution from workflow,
     * calls the DELETE /workflows/{workflowId}/executions/{executionId} endpoint.
     *
     * @see Client#executeWorkflow
     * @param workflowId Id of the workflow
     * @param executionId Id of the execution
     * @return WorkflowExecution response from REST API
     * @throws IOException General IOException
     * @throws APIException Raised when API returns an erroneous status code
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public JSONObject deleteWorkflowExecution(
        String workflowId,
        String executionId
    ) throws IOException, APIException, MissingAccessTokenException {
        String path = "/workflows/" + workflowId + "/executions/" + executionId;
        HttpUriRequest request = this.createAuthorizedRequest("DELETE", path);
        String response = this.executeRequest(request);
        return new JSONObject(response);
    }

    private String executeRequest(HttpUriRequest request) throws IOException, APIException {
        HttpResponse httpResponse= this.httpClient.execute(request);
        HttpEntity responseEntity = httpResponse.getEntity();
        StatusLine statusLine = httpResponse.getStatusLine();
        int status = statusLine.getStatusCode();

        if (status == HttpStatus.SC_NO_CONTENT) {
            JSONObject response = new JSONObject();
            response.put("Your request executed successfully", String.valueOf(status));
            return response.toString();
        }
        else if (status == HttpStatus.SC_FORBIDDEN) {
            throw new APIException("Credentials provided are not valid");
        }
        else if (status == 429) {
            throw new APIException("You have reached the limit of requests per second");
        }
        else if (status > 299) {
            throw new APIException(status, statusLine.getReasonPhrase());
        }

        return EntityUtils.toString(responseEntity);
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
            case "PATCH": {
                request = new HttpPatch(uri);

                body = jsonBody.toString().getBytes();
                ByteArrayEntity entity = new ByteArrayEntity(body);
                ((HttpPatch) request).setEntity(entity);
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

    private List<NameValuePair> getQueryParameters(ListResourcesOptions options){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        if (options != null) {
            parameters = options.addOptions(parameters);
        }

        return parameters;
    }

    private List<NameValuePair> getQueryParameters(DeleteResourcesOptions options){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        if (options != null) {
            parameters = options.addOptions(parameters);
        }

        return parameters;
    }
}
