package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;


public class DocumentsTest extends ClientTest {
    private void assertDocument(JSONObject document) throws IOException {
        Assert.assertTrue(document.has("batchId"));
        Assert.assertTrue(document.has("consentId"));
        Assert.assertTrue(document.has("content"));
        Assert.assertTrue(document.has("documentId"));
    }

    private void assertDocuments(JSONObject documents) throws IOException {
        Assert.assertTrue(documents.has("documents"));
        Assert.assertTrue(documents.has("nextToken"));

        for (Object document: documents.getJSONArray("documents")) {
            this.assertDocument((JSONObject) document);
        }
    }

    @Test
    public void testCreateDocument() throws IOException, APIException, MissingAccessTokenException {
        JSONObject document = this.client.createDocument(TestUtils.content(), ContentType.PDF);
        this.assertDocument(document);
    }

    @Test
    public void testCreateDocumentWithOptions() throws IOException, APIException, MissingAccessTokenException {
        JSONArray groundTruth = new JSONArray();
        groundTruth.put(new JSONObject(){{ put("label", "totalAmount"); put("value", "100.00"); }});
        CreateDocumentOptions options = new CreateDocumentOptions()
            .setConsentId(TestUtils.consentId())
            .setBatchId(TestUtils.batchId())
            .setGroundTruth(groundTruth);
        JSONObject document = this.client.createDocument(TestUtils.content(), ContentType.PDF, options);
        this.assertDocument(document);
    }

    @Test
    public void testCreateDocumentWithInputStreamAndOptions() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(TestUtils.content());
        JSONArray groundTruth = new JSONArray();
        groundTruth.put(new JSONObject(){{ put("label", "totalAmount"); put("value", "100.00"); }});
        CreateDocumentOptions options = new CreateDocumentOptions()
            .setConsentId(TestUtils.consentId())
            .setBatchId(TestUtils.batchId())
            .setGroundTruth(groundTruth);
        JSONObject document = this.client.createDocument(input, ContentType.PDF, options);
        this.assertDocument(document);

    }
    @Test
    public void testCreateDocumentWithInputStream() throws IOException, APIException, MissingAccessTokenException {
        InputStream input = new ByteArrayInputStream(TestUtils.content());
        JSONObject document = this.client.createDocument(input, ContentType.PDF);
        this.assertDocument(document);
    }

    @Test
    public void testListDocuments() throws IOException, APIException, MissingAccessTokenException {
        JSONObject documents = this.client.listDocuments();
        this.assertDocuments(documents);
    }

    @Test
    public void testListDocumentsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListDocumentsOptions options = new ListDocumentsOptions()
            .setConsentId(TestUtils.consentId())
            .setBatchId(TestUtils.batchId())
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject documents = this.client.listDocuments(options);
        this.assertDocuments(documents);
    }

    @Test
    public void testDeleteDocuments() throws IOException, APIException, MissingAccessTokenException {
        JSONObject documents = this.client.deleteDocuments();
        this.assertDocuments(documents);
    }

    @Test
    public void testDeleteDocumentsWithConsentId() throws IOException, APIException, MissingAccessTokenException {
        DeleteDocumentsOptions options = new DeleteDocumentsOptions()
            .setConsentId(new String[] {TestUtils.consentId()});
        JSONObject documents = this.client.deleteDocuments(options);
        this.assertDocuments(documents);
    }

    @Test
    public void testDeleteDocumentsWithBatchId() throws IOException, APIException, MissingAccessTokenException {
        DeleteDocumentsOptions options = new DeleteDocumentsOptions()
            .setBatchId(new String[] {TestUtils.batchId()});
        JSONObject documents = this.client.deleteDocuments(options);
        this.assertDocuments(documents);
    }

    @Test
    public void testGetDocument() throws IOException, APIException, MissingAccessTokenException {
        JSONObject document = this.client.getDocument(TestUtils.documentId());
        this.assertDocument(document);
    }

    @Test
    public void testUpdateDocument() throws IOException, APIException, MissingAccessTokenException {
        JSONArray groundTruth = new JSONArray();
        groundTruth.put(new JSONObject(){{ put("label", "totalAmount"); put("value", "100.00"); }});
        JSONObject document = this.client.updateDocument(TestUtils.documentId(), groundTruth);
        this.assertDocument(document);
    }
}
