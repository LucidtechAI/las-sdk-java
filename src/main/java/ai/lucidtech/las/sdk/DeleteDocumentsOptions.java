package ai.lucidtech.las.sdk;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class DeleteDocumentsOptions extends DeleteResourcesOptions<DeleteDocumentsOptions> {
    private String[] batchId;
    private String[] datasetId;
    private String[] consentId;

    public DeleteDocumentsOptions setConsentId(String[] consentId) {
        this.consentId = consentId;
        return this;
    }

    public DeleteDocumentsOptions setDatasetId(String[] datasetId) {
        this.datasetId = datasetId;
        return this;
    }

    public DeleteDocumentsOptions setBatchId(String[] batchId) {
        this.batchId = batchId;
        return this;
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters) {
        this.addOption(parameters, "batchId", this.batchId);
        this.addOption(parameters, "datasetId", this.datasetId);
        this.addOption(parameters, "consentId", this.consentId);
        return super.addOptions(parameters);
    }
}
