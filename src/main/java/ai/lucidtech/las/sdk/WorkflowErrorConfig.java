package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class WorkflowErrorConfig extends Options {
    private String email;
    private Boolean manualRetry;

    public WorkflowErrorConfig setEmail(String email) {
        this.email = email;
        return this;
    }

    public WorkflowErrorConfig setManualRetry(Boolean manualRetry) {
        this.manualRetry = manualRetry;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "email", this.email);
        this.addOption(body, "manualRetry", this.manualRetry);
        return body;
    }
}
