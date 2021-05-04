package ai.lucidtech.las.sdk;


public enum WorkflowExecutionStatus {
    SUCCEEDED("succeeded"),
    FAILED("failed"),
    REJECTED("rejected");

    public final String value;

    WorkflowExecutionStatus(String value) {
        this.value = value;
    }
}
