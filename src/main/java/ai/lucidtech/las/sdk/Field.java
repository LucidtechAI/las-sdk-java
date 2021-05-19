package ai.lucidtech.las.sdk;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;


public class Field extends Options {
    public String name;
    private FieldType fieldType;
    private Integer maxLength;
    private String description;

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
        this.description = description;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        if (this.fieldType != null) {
            this.addOption(body, "type", this.fieldType.value);
        }
        this.addOption(body, "maxLength", this.maxLength);
        this.addOption(body, "description", this.description == null ? "" : this.description); // TODO: Remove hack
        return body;
    }
}
