package ai.lucidtech.las.sdk;

import org.json.JSONObject;

import java.util.Base64;

public class CreateUserOptions {
    private String name;
    private String avatar;


    public CreateUserOptions(){
        this.name = null;
        this.avatar = null;
    }

    public CreateUserOptions(String name, String avatar){
        this.name = name;
        this.avatar = avatar;
    }

    public CreateUserOptions setName(String name){
        this.name = name;
        return this;
    }

    public CreateUserOptions setAvatar(String avatar){
        this.avatar = avatar;
        return this;
    }

    public CreateUserOptions setAvatar(byte[] avatar){
        this.avatar = Base64.getEncoder().encodeToString(avatar);
        return this;
    }

    public JSONObject addOptions(JSONObject body){
        if (this.name != null) {
            body.put("name", this.name);
        }
        if (this.avatar != null) {
            body.put("avatar", this.avatar);
        }
        return body;
    }

    public JSONObject toJson(){
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
