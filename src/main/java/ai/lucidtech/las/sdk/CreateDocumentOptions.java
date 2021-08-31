package ai.lucidtech.las.sdk;

import org.json.JSONArray;
import org.json.JSONObject;


public class CreateDocumentOptions extends Options {
    private String consentId;
    private String batchId;
    private JSONArray groundTruth;
    private Integer retentionInDays;

    public CreateDocumentOptions setConsentId(String consentId) {
        this.consentId = consentId;
        return this;
    }

    public CreateDocumentOptions setBatchId(String batchId) {
        this.batchId = batchId;
        return this;
    }

    public CreateDocumentOptions setGroundTruth(JSONArray groundTruth) {
        this.groundTruth = groundTruth;
        return this;
    }

    public CreateDocumentOptions setRetentionInDays(Integer retentionInDays) {
        this.retentionInDays = retentionInDays;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "consentId", this.consentId);
        this.addOption(body, "batchId", this.batchId);
        this.addOption(body, "groundTruth", this.groundTruth);
        this.addOption(body, "retentionInDays", this.retentionInDays);
        return body;
    }
}
