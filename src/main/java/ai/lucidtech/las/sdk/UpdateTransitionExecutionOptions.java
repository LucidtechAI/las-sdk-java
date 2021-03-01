package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class UpdateTransitionExecutionOptions {
    private JSONObject output;
    private JSONObject error;
    private String startTime;

    public UpdateTransitionExecutionOptions(){
        this.output = null;
        this.error = null;
        this.startTime = null;
    }

    public UpdateTransitionExecutionOptions setOutput(JSONObject error){
        this.output = output;
        return this;
    }

    public UpdateTransitionExecutionOptions setError(JSONObject output){
        this.error = error;
        return this;
    }

    public UpdateTransitionExecutionOptions setStartTime(String startTime){
        this.startTime = startTime;
        return this;
    }

    public JSONObject addOptions(JSONObject body){
        if (this.output != null) {
            body.put("output", this.output);
        }
        else if (this.error != null) {
            body.put("error", this.error);
        }
        if (this.startTime != null) {
            body.put("startTime", this.startTime);
        }
        return body;
    }

    public JSONObject toJson(){
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
