package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreateTransitionOptions extends NameAndDescriptionOptions<CreateTransitionOptions> {
    private JSONObject parameters;
    private JSONObject inSchema;
    private JSONObject outSchema;


    public CreateTransitionOptions(){
        this.parameters = null;
        this.inSchema = null;
        this.outSchema = null;
    }

    public CreateTransitionOptions(JSONObject parameters){
        this.parameters = parameters;
        this.inSchema = null;
        this.outSchema = null;
    }

    public CreateTransitionOptions setParameters(JSONObject schema){
        this.parameters = schema;
        return this;
    }

    public CreateTransitionOptions setInSchema(JSONObject schema){
        this.inSchema = schema;
        return this;
    }

    public CreateTransitionOptions setOutSchema(JSONObject schema){
        this.outSchema = schema;
        return this;
    }

    public JSONObject addOptions(JSONObject body){
        if (this.parameters != null) {
            body.put("parameters", this.parameters);
        }
        if (this.inSchema != null) {
            body.put("inputJsonSchema", this.inSchema);
        }
        if (this.outSchema != null) {
            body.put("outputJsonSchema", this.outSchema);
        }
        return super.addOptions(body);
    }

    public JSONObject toJson(){
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
