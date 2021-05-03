package ai.lucidtech.las.sdk;

import java.util.Map;

import org.json.JSONObject;


public class DockerTransitionParameters extends TransitionParameters {
    private String imageUrl;
    private String secretId;
    private Integer memory;
    private Integer cpu;
    private String[] environmentSecrets;
    private Map<String, String> environment;

    public DockerTransitionParameters setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public DockerTransitionParameters setSecretId(String secretId) {
        this.secretId = secretId;
        return this;
    }

    public DockerTransitionParameters setMemory(Integer memory) {
        this.memory = memory;
        return this;
    }

    public DockerTransitionParameters setCpu(Integer cpu) {
        this.cpu = cpu;
        return this;
    }

    public DockerTransitionParameters setEnvironmentSecrets(String[] environmentSecrets) {
        this.environmentSecrets = environmentSecrets;
        return this;
    }

    public DockerTransitionParameters setEnvironment(Map<String, String> environment) {
        this.environment = environment;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "imageUrl", this.imageUrl);
        this.addOption(body, "secretId", this.secretId);
        this.addOption(body, "memory", this.memory);
        this.addOption(body, "cpu", this.cpu);
        this.addOption(body, "environmentSecrets", this.environmentSecrets);
        this.addOption(body, "environment", this.environment);
        return super.addOptions(body);
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        this.addOptions(body);
        return body;
    }
}
