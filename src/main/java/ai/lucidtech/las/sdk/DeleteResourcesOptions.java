package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class DeleteResourcesOptions<T> {
    protected Integer maxResults;
    protected String nextToken;

    public T setMaxResults(int maxResults) {
        this.maxResults = maxResults;
        return (T) this;
    }

    public T setNextToken(String nextToken) {
        this.nextToken = nextToken;
        return (T) this;
    }

    public List<NameValuePair> toList() {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        return this.addOptions(parameters);
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters) {
        if (this.maxResults != null) {
            parameters.add(new BasicNameValuePair("maxResults", Integer.toString(this.maxResults)));
        }
        if (this.nextToken != null) {
            parameters.add(new BasicNameValuePair("nextToken", this.nextToken));
        }
        return parameters;
    }
}
