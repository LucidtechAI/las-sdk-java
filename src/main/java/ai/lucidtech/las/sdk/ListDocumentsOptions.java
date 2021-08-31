package ai.lucidtech.las.sdk;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class ListDocumentsOptions extends ListResourcesOptions<ListDocumentsOptions> {
    private String batchId;
    private String datasetId;
    private String consentId;

    public ListDocumentsOptions setConsentId(String consentId) {
        this.consentId = consentId;
        return this;
    }

    public ListDocumentsOptions setDatasetId(String datasetId) {
        this.datasetId = datasetId;
        return this;
    }

    public ListDocumentsOptions setBatchId(String batchId) {
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
