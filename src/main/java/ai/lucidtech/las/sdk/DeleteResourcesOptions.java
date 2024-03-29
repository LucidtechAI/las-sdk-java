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

    protected void addOption(List<NameValuePair> parameters, String key, String value) {
        if (value != null) {
            parameters.add(new BasicNameValuePair(key, value));
        }
    }

    protected void addOption(List<NameValuePair> parameters, String key, String[] value) {
        if (value != null) {
            for (String v : value) {
                parameters.add(new BasicNameValuePair(key, v));
            }
        }
    }

    protected void addOption(List<NameValuePair> parameters, String key, Integer value) {
        if (value != null) {
            parameters.add(new BasicNameValuePair(key, Integer.toString(value)));
        }
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters) {
        this.addOption(parameters, "maxResults", this.maxResults);
        this.addOption(parameters, "nextToken", this.nextToken);
        return parameters;
    }
}
