package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreateTransitionOptions extends NameAndDescriptionOptions<CreateTransitionOptions> {
    private JSONObject parameters;
    private JSONObject inputJsonSchema;
    private JSONObject outputJsonSchema;


    public CreateTransitionOptions(){
        this.parameters = null;
        this.inputJsonSchema = null;
        this.outputJsonSchema = null;
    }

    public CreateTransitionOptions(JSONObject parameters){
        this.parameters = parameters;
        this.inputJsonSchema = null;
        this.outputJsonSchema = null;
    }

    public CreateTransitionOptions setParameters(JSONObject schema){
        this.parameters = schema;
        return this;
    }

    public CreateTransitionOptions setInputJsonSchema(JSONObject schema){
        this.inputJsonSchema = schema;
        return this;
    }

    public CreateTransitionOptions setOutputJsonSchema(JSONObject schema){
        this.outputJsonSchema = schema;
        return this;
    }

    public JSONObject addOptions(JSONObject body){
        if (this.parameters != null) {
            body.put("parameters", this.parameters);
        }
        if (this.inputJsonSchema != null) {
            body.put("inputJsonSchema", this.inputJsonSchema);
        }
        if (this.outputJsonSchema != null) {
            body.put("outputJsonSchema", this.outputJsonSchema);
        }
        return super.addOptions(body);
    }

    public JSONObject toJson(){
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
