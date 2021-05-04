package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class ListTransitionsOptions extends ListResourcesOptions<ListTransitionsOptions> {
    private TransitionType transitionType;

    public ListTransitionsOptions setTransitionType(TransitionType transitionType) {
        this.transitionType = transitionType;
        return this;
    }

    public List<NameValuePair> toList() {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        this.addOption(parameters, "transitionType", this.transitionType.value);
        return super.addOptions(parameters);
    }
}
