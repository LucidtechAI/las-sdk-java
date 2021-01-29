package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;



public class ListResourcesOptions<T> {
    protected int maxResults;
    protected String nextToken;


    public ListResourcesOptions(){
        this.maxResults = 0;
        this.nextToken = null;
    }

    public ListResourcesOptions(int maxResults){
        this(maxResults, null);
    }

    public ListResourcesOptions(String nextToken){
        this(0, nextToken);
    }

    public ListResourcesOptions(int maxResults, String nextToken){
        this.maxResults = maxResults;
        this.nextToken = nextToken;
    }

    public T setMaxResults(int maxResults){
        this.maxResults = maxResults;
        return (T) this;
    }

    public T setNextToken(String nextToken){
        this.nextToken = nextToken;
        return (T) this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        return this.addOptions(parameters);
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters){
        if( this.maxResults != 0){
            parameters.add(new BasicNameValuePair("maxResults", Integer.toString(this.maxResults)));
        }
        if(this.nextToken != null){
            parameters.add(new BasicNameValuePair("nextToken", this.nextToken));
        }
        return parameters;
    }
}
