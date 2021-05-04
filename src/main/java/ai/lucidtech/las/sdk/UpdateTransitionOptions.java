package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class UpdateTransitionOptions extends NameAndDescriptionOptions<UpdateTransitionOptions> {
    private JSONObject inputJsonSchema;
    private JSONObject outputJsonSchema;

    public UpdateTransitionOptions setInputJsonSchema(JSONObject schema) {
        this.inputJsonSchema = schema;
        return this;
    }

    public UpdateTransitionOptions setOutputJsonSchema(JSONObject schema) {
        this.outputJsonSchema = schema;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "inputJsonSchema", this.inputJsonSchema);
        this.addOption(body, "outputJsonSchema", this.outputJsonSchema);
        return super.addOptions(body);
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
