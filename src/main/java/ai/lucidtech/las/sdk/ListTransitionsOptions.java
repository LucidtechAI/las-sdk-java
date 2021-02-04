package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class ListTransitionsOptions extends ListResourcesOptions<ListTransitionsOptions> {
    private TransitionType transitionType;

    public ListTransitionsOptions(){
        super();
        this.transitionType = null;
    }

    public ListTransitionsOptions setTransitionType(TransitionType transitionType){
        this.transitionType = transitionType;
        return this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if( this.transitionType != null){
            parameters.add(new BasicNameValuePair("transitionType", this.transitionType.value));
        }
        return super.addOptions(parameters);
    }
}
