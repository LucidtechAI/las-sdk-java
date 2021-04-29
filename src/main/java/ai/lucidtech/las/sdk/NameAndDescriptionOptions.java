package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class NameAndDescriptionOptions<T> extends Options {
    private String name;
    private String description;

    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    public T setDescription(String description) {
        this.description = description;
        return (T) this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "name", this.name);
        this.addOption(body, "description", this.description);
        return body;
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
