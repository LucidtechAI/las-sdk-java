package ai.lucidtech.las.sdk;

public class Field {
    private String label;
    private String value;
    private Float confidence;

    public Field(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public Field(String label, String value, Float confidence) {
        this.label = label;
        this.value = value;
        this.confidence = confidence;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public Float getConfidence() {
        return confidence;
    }
}
