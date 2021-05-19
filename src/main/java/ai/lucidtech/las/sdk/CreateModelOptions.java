package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreateModelOptions extends NameAndDescriptionOptions<CreateModelOptions> {
    private PreprocessConfig preprocessConfig;

    public CreateModelOptions setPreprocessConfig(PreprocessConfig preprocessConfig) {
        this.preprocessConfig = preprocessConfig;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "preprocessConfig", this.preprocessConfig);
        return super.addOptions(body);
    }
}
