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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.*;


public class OptionalListResource<T> {
    protected int maxResults;
    protected String nextToken;


    public OptionalListResource(){
        this.maxResults = 0;
        this.nextToken = null;
    }

    public OptionalListResource(int maxResults){
        this(maxResults, null);
    }

    public OptionalListResource(String nextToken){
        this(0, nextToken);
    }

    public OptionalListResource(int maxResults, String nextToken){
        this.maxResults = maxResults;
        this.nextToken = nextToken;
    }

    public T setMaxResults(int maxResults){
        this.maxResults = maxResults;
        return (T) this;
    }

    public OptionalListResource setNextToken(String nextToken){
        this.nextToken = nextToken;
        return this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if( this.maxResults != 0){
            parameters.add(new BasicNameValuePair("maxResults", new Integer(this.maxResults).toString()));
        }
        if(this.nextToken != null){
            parameters.add(new BasicNameValuePair("nextToken", this.nextToken));
        }
        return parameters;
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters){
        if( this.maxResults != 0){
            parameters.add(new BasicNameValuePair("maxResults", new Integer(this.maxResults).toString()));
        }
        if(this.nextToken != null){
            parameters.add(new BasicNameValuePair("nextToken", this.nextToken));
        }
        return parameters;
    }
}
