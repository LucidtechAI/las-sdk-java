package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class ListTransitionExecutionsOptions extends ListSortablesOptions<ListTransitionExecutionsOptions> {
    private List<String> executionId;

    public ListTransitionExecutionsOptions(){
        super();
        this.executionId = null;
    }

    public ListTransitionExecutionsOptions setExecutionId(List<String> executionId){
        this.executionId = executionId;
        return this;
    }

    public ListTransitionExecutionsOptions setExecutionId(String executionId){
        this.executionId = Arrays.asList(executionId);
        return this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if( this.executionId != null){
            for (String e : this.executionId) {
                parameters.add(new BasicNameValuePair("executionId", e));
            }
        }
        return super.addOptions(parameters);
    }
}
