package ai.lucidtech.las.sdk;

import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.util.Map;


public class UpdateSecretOptions extends NameAndDescriptionOptions<UpdateSecretOptions> {
    private JSONObject data;

    public UpdateSecretOptions(){
        super();
        this.data = null;
    }

    public UpdateSecretOptions setData(JSONObject data){
        this.data = data;
        return this;
    }

    public UpdateSecretOptions setData(Map<String, String> data){
        this.data = new JSONObject(data);
        return this;
    }

    public JSONObject addOptions(JSONObject body){
        if (this.data != null) {
            body.put("data", this.data);
        }
        return super.addOptions(body);
    }

    public JSONObject toJson(){
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
