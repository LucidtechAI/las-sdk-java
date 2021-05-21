package ai.lucidtech.las.sdk;


public enum FieldType {
    DATE("date"),
    AMOUNT("amount"),
    NUMBER("number"),
    LETTER("letter"),
    PHONE("phone"),
    ALPHANUM("alphanum"),
    ALPHANUMEXT("alphanumext"),
    ALL("all");

    public final String value;

    FieldType(String value) {
        this.value = value;
    }
}
