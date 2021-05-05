package ai.lucidtech.las.sdk;

import java.util.Map;
import org.json.JSONObject;


public class WorkflowCompletedConfig extends Options {
    private String imageUrl;
    private String secretId;
    private String[] environmentSecrets;
    private Map<String, String> environment;

    public WorkflowCompletedConfig setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public WorkflowCompletedConfig setSecretId(String secretId) {
        this.secretId = secretId;
        return this;
    }

    public WorkflowCompletedConfig setEnvironmentSecrets(String[] environmentSecrets) {
        this.environmentSecrets = environmentSecrets;
        return this;
    }

    public WorkflowCompletedConfig setEnvironment(Map<String, String> environment) {
        this.environment = environment;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "imageUrl", this.imageUrl);
        this.addOption(body, "secretId", this.secretId);
        this.addOption(body, "environmentSecrets", this.environmentSecrets);
        this.addOption(body, "environment", this.environment);
        return body;
    }
}
