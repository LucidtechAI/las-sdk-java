package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class ListTransitionsOptions extends ListResourcesOptions<ListTransitionsOptions> {
    private String transitionType;

    public ListTransitionsOptions(){
        super();
        this.transitionType = null;
    }

    public ListTransitionsOptions setTransitionType(String transitionType){
        this.transitionType = transitionType;
        return this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if( this.transitionType != null){
            parameters.add(new BasicNameValuePair("transitionType", this.transitionType));
        }
        return super.addOptions(parameters);
    }
}
