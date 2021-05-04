package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreateWorkflowOptions extends NameAndDescriptionOptions<CreateWorkflowOptions> {
    private WorkflowCompletedConfig completedConfig;
    private WorkflowErrorConfig errorConfig;

    public CreateWorkflowOptions setCompletedConfig(WorkflowCompletedConfig completedConfig) {
        this.completedConfig = completedConfig;
        return this;
    }

    public CreateWorkflowOptions setErrorConfig(WorkflowErrorConfig errorConfig) {
        this.errorConfig = errorConfig;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "completedConfig", this.completedConfig);
        this.addOption(body, "errorConfig", this.errorConfig);
        return super.addOptions(body);
    }
}
