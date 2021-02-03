package ai.lucidtech.las.sdk;


public enum Order{
    ASCENDING("ascending"),
    DESCENDING("descending");

    public final String value;

    Order(String value) {
        this.value = value;
    }
}
