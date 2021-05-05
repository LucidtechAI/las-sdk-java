package ai.lucidtech.las.sdk;

import org.json.JSONObject;

import java.util.Base64;

public class UserOptions<T> extends Options {
    private String name;
    private String avatar;

    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    public T setAvatar(String avatar) {
        this.avatar = avatar;
        return (T) this;
    }

    public T setAvatar(byte[] avatar) {
        this.avatar = Base64.getEncoder().encodeToString(avatar);
        return (T) this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "name", this.name);
        this.addOption(body, "avatar", this.avatar);
        return body;
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
