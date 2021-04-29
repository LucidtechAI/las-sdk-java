package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public abstract class Options {
    protected void addOption(JSONObject body, String key, String value) {
        if (value != null) {
            body.put(key, value);
        }
    }

    protected void addOption(JSONObject body, String key, String[] value) {
        if (value != null) {
            body.put(key, value);
        }
    }

    protected void addOption(JSONObject body, String key, Boolean value) {
        if (value != null) {
            body.put(key, value);
        }
    }

    abstract public JSONObject addOptions(JSONObject body);
}
