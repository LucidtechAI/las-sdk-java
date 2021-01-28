package ai.lucidtech.las.sdk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.AuthCache;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class OptionalNameAndDescription {
    private String name;
    private String description;

    public OptionalNameAndDescription(){
        this.name = null;
        this.description = null;
    }

    public OptionalNameAndDescription(String name, String description){
        this.name = name;
        this.description = description;
    }

    public OptionalNameAndDescription setName(String name){
        this.name = name;
        return this;
    }

    public OptionalNameAndDescription setDescription(String description){
        this.description = description;
        return this;
    }

    JSONObject addOptions(JSONObject body){
        body.put("name", this.name);
        body.put("description", this.description);
        return body;
    }

}
