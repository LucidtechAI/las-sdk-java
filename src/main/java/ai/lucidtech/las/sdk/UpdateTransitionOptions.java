package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class UpdateTransitionOptions extends NameAndDescriptionOptions<UpdateTransitionOptions> {
    private JSONObject inSchema;
    private JSONObject outSchema;


    public UpdateTransitionOptions(){
        this.inSchema = null;
        this.outSchema = null;
    }

    public UpdateTransitionOptions setInSchema(JSONObject schema){
        this.inSchema = schema;
        return this;
    }

    public UpdateTransitionOptions setOutSchema(JSONObject schema){
        this.outSchema = schema;
        return this;
    }

    public JSONObject addOptions(JSONObject body){
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
