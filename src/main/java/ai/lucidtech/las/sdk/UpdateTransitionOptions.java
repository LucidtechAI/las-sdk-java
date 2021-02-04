package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class UpdateTransitionOptions extends NameAndDescriptionOptions<UpdateTransitionOptions> {
    private JSONObject inputJsonSchema;
    private JSONObject outputJsonSchema;


    public UpdateTransitionOptions(){
        this.inputJsonSchema = null;
        this.outputJsonSchema = null;
    }

    public UpdateTransitionOptions setInputJsonSchema(JSONObject schema){
        this.inputJsonSchema = schema;
        return this;
    }

    public UpdateTransitionOptions setOutputJsonSchema(JSONObject schema){
        this.outputJsonSchema = schema;
        return this;
    }

    public JSONObject addOptions(JSONObject body){
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
