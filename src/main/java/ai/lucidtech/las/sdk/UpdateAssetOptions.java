package ai.lucidtech.las.sdk;

import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.util.Base64;



public class UpdateAssetOptions extends NameAndDescriptionOptions<UpdateAssetOptions> {
    private byte[] content;

    public UpdateAssetOptions(){
        super();
        this.content = null;
    }

    public UpdateAssetOptions setContent(byte[] content){
        this.content = content;
        return this;
    }

    public UpdateAssetOptions setContent(InputStream content) throws IOException {
        this.content = IOUtils.toByteArray(content);
        return this;
    }

    public JSONObject addOptions(JSONObject body){
        if (this.content != null) {
            body.put("content", Base64.getEncoder().encodeToString(this.content));
        }
        return super.addOptions(body);
    }

    public JSONObject toJson(){
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
