package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;


public class DocumentsTest extends ClientTest {

    private void assertDocument(JSONObject document) throws IOException {
        Assert.assertTrue(document.has("documentId"));
        Assert.assertTrue(document.has("content"));
        Assert.assertTrue(document.has("consentId"));
        Assert.assertTrue(document.has("batchId"));
    }

    @Test
    public void testCreateDocument() throws IOException, APIException, MissingAccessTokenException {
        JSONObject document = this.client.createDocument(Service.content(), ContentType.PDF);
        this.assertDocument(document);
    }

    @Test
    public void testCreateDocumentWithOptions() throws IOException, APIException, MissingAccessTokenException {
        JSONArray groundTruth = new JSONArray();
        groundTruth.put(new JSONObject(){{ put("label", "totalAmount"); put("value", "100.00"); }});
        CreateDocumentOptions options = new CreateDocumentOptions()
            .setConsentId(Service.consentId())
            .setBatchId(Service.batchId())
            .setGroundTruth(groundTruth);
        JSONObject document = this.client.createDocument(Service.content(), ContentType.PDF, options);
        this.assertDocument(document);
    }

    @Test
    public void testCreateDocumentWithInputStreamAndOptions() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(Service.content());
        JSONArray groundTruth = new JSONArray();
        groundTruth.put(new JSONObject(){{ put("label", "totalAmount"); put("value", "100.00"); }});
        CreateDocumentOptions options = new CreateDocumentOptions()
            .setConsentId(Service.consentId())
            .setBatchId(Service.batchId())
            .setGroundTruth(groundTruth);
        JSONObject document = this.client.createDocument(input, ContentType.PDF, options);
        this.assertDocument(document);

    }
    @Test
    public void testCreateDocumentWithInputStream() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(Service.content());
        JSONObject document = this.client.createDocument(input, ContentType.PDF);
        this.assertDocument(document);
    }

    @Test
    public void testListDocuments() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listDocuments();
        JSONArray documents = response.getJSONArray("documents");
        Assert.assertNotNull(documents);
    }

    @Test
    public void testListDocumentsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListDocumentsOptions options = new ListDocumentsOptions()
            .setConsentId(Service.consentId())
            .setBatchId(Service.batchId())
            .setMaxResults(30)
            .setNextToken("foo");
        JSONObject response = this.client.listDocuments(options);
        JSONArray documents = response.getJSONArray("documents");
        Assert.assertNotNull(documents);
    }

    @Test
    public void testDeleteDocuments() throws IOException, APIException, MissingAccessTokenException {
        JSONObject document = this.client.deleteDocuments();
        JSONArray documents = document.getJSONArray("documents");
        Assert.assertNotNull(documents);
    }

    @Test
    public void testDeleteDocumentsWithConsentId() throws IOException, APIException, MissingAccessTokenException {
        JSONObject document = this.client.deleteDocuments(Service.consentId());
        JSONArray documents = document.getJSONArray("documents");
        Assert.assertNotNull(documents);
    }

    @Test
    public void testGetDocument() throws IOException, APIException, MissingAccessTokenException {
        JSONObject document = this.client.getDocument(Service.documentId());
        this.assertDocument(document);
    }

    @Test
    public void testUpdateDocument() throws IOException, APIException, MissingAccessTokenException {
        JSONArray groundTruth = new JSONArray();
        groundTruth.put(new JSONObject(){{ put("label", "totalAmount"); put("value", "100.00"); }});
        JSONObject document = this.client.updateDocument(Service.documentId(), groundTruth);
        this.assertDocument(document);
    }
}
