package ai.lucidtech.las.sdk;

import org.json.JSONArray;
import org.json.JSONObject;


public class CreateDocumentOptions extends Options {
    private String consentId;
    private String batchId;
    private JSONArray groundTruth;

    public CreateDocumentOptions() {
        this.consentId = null;
        this.batchId = null;
        this.groundTruth = null;
    }

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


    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "consentId", this.consentId);
        this.addOption(body, "batchId", this.batchId);
        this.addOption(body, "groundTruth", this.groundTruth);
        return body;
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
