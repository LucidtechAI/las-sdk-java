package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class NameAndDescriptionOptions<T> extends Options {
    private NullableString name = new NullableString();
    private NullableString description = new NullableString();

    public T setName(String name) {
        this.name.setValue(name);
        return (T) this;
    }

    public T setDescription(String description) {
        this.description.setValue(description);
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
