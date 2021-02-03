package ai.lucidtech.las.sdk;

import org.json.JSONObject;
import org.json.JSONArray;


public class CreateDocumentOptions {
    private String consentId;
    private String batchId;
    private JSONArray groundTruth;


    public CreateDocumentOptions(){
        this.consentId = null;
        this.batchId = null;
        this.groundTruth = null;
    }

    public CreateDocumentOptions setConsentId(String consentId){
        this.consentId = consentId;
        return this;
    }

    public CreateDocumentOptions setBatchId(String batchId){
        this.batchId = batchId;
        return this;
    }

    public CreateDocumentOptions setBatchId(JSONArray groundTruth){
        this.groundTruth = groundTruth;
        return this;
    }


    public JSONObject addOptions(JSONObject body){
        if (this.consentId != null) {
            body.put("consentId", this.consentId);
        }
        if (this.batchId != null) {
            body.put("batchId", this.batchId);
        }
        if (this.groundTruth != null) {
            body.put("groundTruth", groundTruth);
        }
        return body;
    }

    public JSONObject toJson(){
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
