package ai.lucidtech.las.sdk;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class Prediction {
    private String documentId;
    private String consentId;
    private String modelName;
    private List<Field> fields;

    public Prediction(String documentId, String consentId, String modelName, JSONObject predictionResponse) {
        this.documentId = documentId;
        this.consentId = consentId;
        this.modelName = modelName;

        JSONArray fields = predictionResponse.getJSONArray("predictions");
        this.fields = StreamSupport.stream(fields.spliterator(), false)
                .map(o -> (JSONObject)o)
                .map(Prediction::toField)
                .collect(Collectors.toList());
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getConsentId() {
        return consentId;
    }

    public String getModelName() {
        return modelName;
    }

    public List<Field> getFields() {
        return fields;
    }

    private static Field toField(JSONObject fieldObject) {
        String label = fieldObject.getString("label");
        String value = fieldObject.getString("value");
        Float confidence = fieldObject.getFloat("confidence");
        return new Field(label, value, confidence);
    }
}
