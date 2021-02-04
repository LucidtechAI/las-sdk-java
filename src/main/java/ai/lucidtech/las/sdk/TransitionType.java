package ai.lucidtech.las.sdk;


public enum TransitionType{
    MANUAL("manual"),
    DOCKER("docker");

    public final String value;

    TransitionType(String value) {
        this.value = value;
    }
}
