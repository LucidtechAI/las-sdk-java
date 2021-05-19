package ai.lucidtech.las.sdk;

import java.util.ArrayList;

import org.json.JSONObject;


public class FieldConfig extends Options {
    private ArrayList<Field> fields = new ArrayList<Field>();

    public FieldConfig addField(Field field) {
        this.fields.add(field);
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        for (Field field : this.fields) {
            this.addOption(body, field.name, field);
        }
        return body;
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();

        for (Field field : this.fields) {
            this.addOption(body, field.name, field);
        }
        return body;
    }
}
