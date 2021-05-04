package ai.lucidtech.las.sdk;

import java.util.Map;

import org.json.JSONArray;
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

    protected void addOption(JSONObject body, String key, Map<String, String> value) {
        if (value != null) {
            JSONObject mapBody = new JSONObject();
            for (Map.Entry<String, String> entry: value.entrySet()) {
                mapBody.put(entry.getKey(), entry.getValue());
            }
            body.put(key, mapBody);
        }
    }

    protected void addOption(JSONObject body, String key, Boolean value) {
        if (value != null) {
            body.put(key, value);
        }
    }

    protected void addOption(JSONObject body, String key, Integer value) {
        if (value != null) {
            body.put(key, value);
        }
    }

    protected void addOption(JSONObject body, String key, JSONArray value) {
        if (value != null) {
            body.put(key, value);
        }
    }

    protected void addOption(JSONObject body, String key, JSONObject value) {
        if (value != null) {
            body.put(key, value);
        }
    }

    protected void addOption(JSONObject body, String key, Options value) {
        if (value != null) {
            JSONObject options = new JSONObject();
            body.put(key, value.addOptions(options));
        }
    }

    abstract public JSONObject addOptions(JSONObject body);
}
