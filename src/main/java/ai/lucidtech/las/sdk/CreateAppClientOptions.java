package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreateAppClientOptions extends NameAndDescriptionOptions<CreateAppClientOptions> {
    private String[] callbackUrls;
    private String[] logoutUrls;
    private Boolean generateSecret;

    public CreateAppClientOptions setCallbackUrls(String[] callbackUrls) {
        this.callbackUrls = callbackUrls;
        return this;
    }

    public CreateAppClientOptions setLogoutUrls(String[] logoutUrls) {
        this.logoutUrls = logoutUrls;
        return this;
    }

    public CreateAppClientOptions setGenerateSecret(Boolean generateSecret) {
        this.generateSecret = generateSecret;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "callbackUrls", this.callbackUrls);
        this.addOption(body, "logoutUrls", this.logoutUrls);
        this.addOption(body, "generateSecret", this.generateSecret);
        return super.addOptions(body);
    }
}
