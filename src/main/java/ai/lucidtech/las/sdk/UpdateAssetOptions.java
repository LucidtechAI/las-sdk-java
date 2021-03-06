package ai.lucidtech.las.sdk;

import org.apache.commons.io.IOUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.util.Base64;


public class UpdateAssetOptions extends NameAndDescriptionOptions<UpdateAssetOptions> {
    private String content;

    public UpdateAssetOptions setContent(byte[] content) {
        this.content = Base64.getEncoder().encodeToString(content);
        return this;
    }

    public UpdateAssetOptions setContent(InputStream content) throws IOException {
        this.content = Base64.getEncoder().encodeToString(IOUtils.toByteArray(content));
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "content", this.content);
        return super.addOptions(body);
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
