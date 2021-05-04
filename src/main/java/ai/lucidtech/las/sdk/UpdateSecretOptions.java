package ai.lucidtech.las.sdk;

import java.util.Map;

import org.json.JSONObject;


public class UpdateSecretOptions extends NameAndDescriptionOptions<UpdateSecretOptions> {
    private JSONObject data;

    public UpdateSecretOptions setData(JSONObject data) {
        this.data = data;
        return this;
    }

    public UpdateSecretOptions setData(Map<String, String> data) {
        this.data = new JSONObject(data);
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "data", this.data);
        return super.addOptions(body);
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
