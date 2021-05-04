package ai.lucidtech.las.sdk;

import java.util.Map;

import org.json.JSONObject;


public class ManualTransitionParameters extends TransitionParameters {
    private Map<String, String> assets;

    public ManualTransitionParameters setAssets(Map<String, String> assets) {
        this.assets = assets;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "assets", this.assets);
        return body;
    }
}
