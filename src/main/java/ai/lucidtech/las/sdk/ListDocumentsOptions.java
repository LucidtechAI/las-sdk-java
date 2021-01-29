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


public class ListDocumentsOptions extends OptionalListResource {
    private String batchId;
    private String consentId;


    public ListDocumentsOptions(){
        super();
        this.batchId = null;
        this.consentId = null;
    }

    public ListDocumentsOptions setConsentId(String consentId){
        this.consentId = consentId;
        return this;
    }

    public ListDocumentsOptions setBatchId(String batchId){
        this.batchId = batchId;
        return this;
    }

    public ListDocumentsOptions setMaxResults(int maxResults){
        this.maxResults = maxResults;
        return this;
    }

    public ListDocumentsOptions setNextToken(String nextToken){
        this.nextToken = nextToken;
        return this;
    }


    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if( this.batchId != null){
            parameters.add(new BasicNameValuePair("batchId", this.batchId));
        }
        if(this.consentId != null){
            parameters.add(new BasicNameValuePair("consentId", this.consentId));
        }

        return super.addOptions(parameters);
    }
}
