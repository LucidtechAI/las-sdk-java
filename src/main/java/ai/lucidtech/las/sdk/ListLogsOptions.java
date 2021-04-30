package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class ListLogsOptions extends ListResourcesOptions<ListLogsOptions> {
    private String transitionId;
    private String transitionExecutionId;
    private String workflowId;
    private String workflowExecutionId;

    public ListLogsOptions setTransitionId(String transitionId) {
        this.transitionId = transitionId;
        return this;
    }

   public ListLogsOptions setTransitionExecutionId(String transitionExecutionId) {
        this.transitionExecutionId = transitionExecutionId;
        return this;
    }

    public ListLogsOptions setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public ListLogsOptions setWorkflowExecutionId(String workflowExecutionId) {
        this.workflowExecutionId = workflowExecutionId;
        return this;
    }

    public List<NameValuePair> toList() {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        this.addOption(parameters, "transitionId", this.transitionId);
        this.addOption(parameters, "transitionExecutionId", this.transitionExecutionId);
        this.addOption(parameters, "workflowId", this.workflowId);
        this.addOption(parameters, "workflowExecutionId", this.workflowExecutionId);
        return super.addOptions(parameters);
    }
}
