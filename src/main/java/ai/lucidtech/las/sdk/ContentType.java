package ai.lucidtech.las.sdk;


public enum ContentType {
    JPEG("image/jpeg"),
    PDF("application/pdf");
    TIFF("image/tiff");
    PNG("image/png");

    private String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public static ContentType fromString(String mimeType) {
        for (ContentType contentType : ContentType.values()) {
            if (contentType.getMimeType().equals(mimeType)) {
                return contentType;
            }
        }

        throw new IllegalArgumentException("No enum matching mime type: " + mimeType);
    }
}
