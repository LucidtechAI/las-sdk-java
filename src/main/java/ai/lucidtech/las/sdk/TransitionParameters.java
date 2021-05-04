package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public abstract class TransitionParameters extends Options {
    abstract public JSONObject addOptions(JSONObject body);
}
