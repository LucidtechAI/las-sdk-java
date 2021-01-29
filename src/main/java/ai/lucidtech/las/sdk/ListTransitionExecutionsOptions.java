package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class ListTransitionExecutionsOptions extends ListResourcesOptions<ListTransitionExecutionsOptions>, ListSortableOptions<ListTransitionExecutionsOptions>{
    private String executionId;

    public ListTransitionExecutionsOptions(){
        super();
        this.executionId = null;
    }

    public ListTransitionExecutionsOptions setExecutionId(List<String> executionId){
        this.executionId = executionId;
        return this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if( this.executionId != null){
            parameters.add(new BasicNameValuePair("executionId", this.executionId));
        }
        return super.addOptions(parameters);
    }
}
