package ai.lucidtech.las.sdk;


public enum ModelStatus {
    INACTIVE("inactive"),
    ACTIVE("active"),
    TRAINING("training");

    public final String value;

    ModelStatus(String value) {
        this.value = value;
    }
}
