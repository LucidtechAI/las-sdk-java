package ai.lucidtech.las.sdk;

import org.json.JSONArray;
import org.json.JSONObject;


public class UpdateDocumentOptions extends Options {
    private String datasetId;
    private JSONArray groundTruth;
    private Integer retentionInDays;

    public UpdateDocumentOptions setDatasetId(String datasetId) {
        this.datasetId = datasetId;
        return this;
    }

    public UpdateDocumentOptions setGroundTruth(JSONArray groundTruth) {
        this.groundTruth = groundTruth;
        return this;
    }

    public UpdateDocumentOptions setRetentionInDays(Integer retentionInDays) {
        this.retentionInDays = retentionInDays;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "datasetId", this.datasetId);
        this.addOption(body, "groundTruth", this.groundTruth);
        this.addOption(body, "retentionInDays", this.retentionInDays);
        return body;
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
