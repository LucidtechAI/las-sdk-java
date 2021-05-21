package ai.lucidtech.las.sdk;


class NullableString {
    public Boolean hasEditedValue;
    private String value;

    public NullableString() {
        this.hasEditedValue = false;
        this.value = null;
    }

    public void setValue(String value) {
        this.value = value;
        this.hasEditedValue = true;
    }
}