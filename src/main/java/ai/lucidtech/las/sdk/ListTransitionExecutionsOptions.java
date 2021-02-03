package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class ListTransitionExecutionsOptions extends ListSortablesOptions<ListTransitionExecutionsOptions> {
    private List<String> executionId;
    private List<TransitionExecutionStatus> status;

    public ListTransitionExecutionsOptions(){
        super();
        this.executionId = null;
        this.status = null;
    }

    public ListTransitionExecutionsOptions setExecutionId(List<String> executionId){
        this.executionId = executionId;
        return this;
    }

    public ListTransitionExecutionsOptions setExecutionId(String executionId){
        this.executionId = Arrays.asList(executionId);
        return this;
    }

    public ListTransitionExecutionsOptions setStatus(List<TransitionExecutionStatus> status){
        this.status = status;
        return this;
    }

    public ListTransitionExecutionsOptions setStatus(TransitionExecutionStatus status){
        this.status = Arrays.asList(status);
        return this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        return this.addOptions(parameters);
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters){

        if( this.executionId != null){
            for (String e : this.executionId) {
                parameters.add(new BasicNameValuePair("executionId", e));
            }
        }
        else if( this.status != null){
            for (TransitionExecutionStatus s : this.status) {
                parameters.add(new BasicNameValuePair("status", s.value));
            }
        }

        return super.addOptions(parameters);
    }
}
