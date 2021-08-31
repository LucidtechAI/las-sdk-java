package ai.lucidtech.las.sdk;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;


public class ListWorkflowExecutionsOptions extends ListSortablesOptions<ListWorkflowExecutionsOptions> {
    private List<WorkflowExecutionStatus> status;

    public ListWorkflowExecutionsOptions setStatus(List<WorkflowExecutionStatus> status) {
        this.status = status;
        return this;
    }

    public ListWorkflowExecutionsOptions setStatus(WorkflowExecutionStatus status) {
        this.status = Arrays.asList(status);
        return this;
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters) {
        if (this.status != null) {
            List<String> statusList = this.status.stream().map(s -> s.value).collect(Collectors.toList());
            this.addOption(parameters, "status", statusList);
        }
        return super.addOptions(parameters);
    }
}
