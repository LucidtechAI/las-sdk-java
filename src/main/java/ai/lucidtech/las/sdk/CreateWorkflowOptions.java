package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreateWorkflowOptions extends NameAndDescriptionOptions<CreateWorkflowOptions> {
    private JSONObject errorConfig;


    public CreateWorkflowOptions(){
        this.errorConfig = null;
    }

    public CreateWorkflowOptions(JSONObject errorConfig){
        this.errorConfig = errorConfig;
    }

    public CreateWorkflowOptions setErrorConfig(JSONObject errorConfig){
        this.errorConfig = errorConfig;
        return this;
    }

    public JSONObject addOptions(JSONObject body){
        if (this.errorConfig != null) {
            body.put("errorConfig", this.errorConfig);
        }
        return super.addOptions(body);
    }

    public JSONObject toJson(){
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
