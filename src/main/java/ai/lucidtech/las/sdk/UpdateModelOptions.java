package ai.lucidtech.las.sdk;

import org.apache.commons.io.IOUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.util.Base64;


public class UpdateModelOptions extends NameAndDescriptionOptions<UpdateModelOptions> {
    private Integer width;
    private Integer height;
    private FieldConfig fieldConfig;
    private ModelStatus modelStatus;
    private PreprocessConfig preprocessConfig;

    public UpdateModelOptions setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public UpdateModelOptions setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public UpdateModelOptions setFieldConfig(FieldConfig fieldConfig) {
        this.fieldConfig = fieldConfig;
        return this;
    }

    public UpdateModelOptions setModelStatus(ModelStatus modelStatus) {
        this.modelStatus = modelStatus;
        return this;
    }

    public UpdateModelOptions setPreprocessConfig(PreprocessConfig preprocessConfig) {
        this.preprocessConfig = preprocessConfig;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "width", this.width);
        this.addOption(body, "height", this.height);
        this.addOption(body, "fieldConfig", this.fieldConfig);
        if (this.modelStatus != null) {
            this.addOption(body, "status", this.modelStatus.value);
        }
        this.addOption(body, "preprocessConfig", this.preprocessConfig);
        return super.addOptions(body);
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
