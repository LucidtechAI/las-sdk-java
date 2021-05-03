package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public abstract class TransitionParameters extends Options {
    public JSONObject addOptions(JSONObject body) {
        return body;
    }

    abstract public JSONObject toJson();
}
