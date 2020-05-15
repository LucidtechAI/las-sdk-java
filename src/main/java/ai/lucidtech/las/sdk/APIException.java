package ai.lucidtech.las.sdk;

public class APIException extends Exception {
    public APIException() {
        super();
    }

    public APIException(String message) {
        super(message);
    }

    public APIException(int code, String message) {
        super("Code" + code + "\nMessage: " + message);
    }
}
