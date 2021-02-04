package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ListWorkflowExecutionsOptions extends ListSortablesOptions<ListWorkflowExecutionsOptions> {
    private List<WorkflowExecutionStatus> status;

    public ListWorkflowExecutionsOptions(){
        super();
        this.status = null;
    }

    public ListWorkflowExecutionsOptions setStatus(List<WorkflowExecutionStatus> status){
        this.status = status;
        return this;
    }

    public ListWorkflowExecutionsOptions setStatus(WorkflowExecutionStatus status){
        this.status = Arrays.asList(status);
        return this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        return this.addOptions(parameters);
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters){
        if( this.status != null){
            for (WorkflowExecutionStatus s : this.status) {
                parameters.add(new BasicNameValuePair("status", s.value));
            }
        }

        return super.addOptions(parameters);
    }
}
