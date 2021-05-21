package ai.lucidtech.las.sdk;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;


public class Field extends Options {
    public String name;
    private FieldType fieldType;
    private Integer maxLength;
    private NullableString description = new NullableString();

    public Field(String name, FieldType fieldType, Integer maxLength) {
        this.name = name;
        this.fieldType = fieldType;
        this.maxLength = maxLength;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    public Field setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public Field setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public Field setDescription(String description) {
        this.description.setValue(description);
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "type", this.fieldType.value);
        this.addOption(body, "maxLength", this.maxLength);
        this.addOption(body, "description", this.description);
        return body;
    }
}
