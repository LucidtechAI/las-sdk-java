package ai.lucidtech.las.sdk;


public enum TransitionExecutionStatus{
    SUCCEEDED("succeeded"),
    FAILED("failed"),
    REJECTED("rejected");

    public final String value;

    TransitionExecutionStatus(String value) {
        this.value = value;
    }
}
