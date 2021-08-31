package ai.lucidtech.las.sdk;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ListTransitionExecutionsOptions extends ListSortablesOptions<ListTransitionExecutionsOptions> {
    private List<String> executionId;
    private List<TransitionExecutionStatus> status;

    public ListTransitionExecutionsOptions setExecutionId(List<String> executionId) {
        this.executionId = executionId;
        return this;
    }

    public ListTransitionExecutionsOptions setExecutionId(String executionId) {
        this.executionId = Arrays.asList(executionId);
        return this;
    }

    public ListTransitionExecutionsOptions setStatus(List<TransitionExecutionStatus> status) {
        this.status = status;
        return this;
    }

    public ListTransitionExecutionsOptions setStatus(TransitionExecutionStatus status) {
        this.status = Arrays.asList(status);
        return this;
    }

    public List<NameValuePair> addOptions(List<NameValuePair> parameters) {
        this.addOption(parameters, "executionId", this.executionId);
        if (this.status != null) {
            List<String> statusList = this.status.stream().map(s -> s.value).collect(Collectors.toList());
            this.addOption(parameters, "status", statusList);
        }
        return super.addOptions(parameters);
    }
}
