package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreateTransitionOptions extends NameAndDescriptionOptions<CreateTransitionOptions> {
    private TransitionParameters parameters;
    private JSONObject inputJsonSchema;
    private JSONObject outputJsonSchema;

    public CreateTransitionOptions setParameters(TransitionParameters parameters) {
        this.parameters = parameters;
        return this;
    }

    public CreateTransitionOptions setInputJsonSchema(JSONObject inputJsonSchema) {
        this.inputJsonSchema = inputJsonSchema;
        return this;
    }

    public CreateTransitionOptions setOutputJsonSchema(JSONObject outputJsonSchema) {
        this.outputJsonSchema = outputJsonSchema;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "parameters", this.parameters);
        this.addOption(body, "inputJsonSchema", this.inputJsonSchema);
        this.addOption(body, "outputJsonSchema", this.outputJsonSchema);
        return super.addOptions(body);
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
