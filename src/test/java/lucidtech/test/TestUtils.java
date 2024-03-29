package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.Random;

import org.json.JSONObject;


public final class TestUtils {
    private TestUtils() {
        throw new AssertionError();
    }

    public static String hexUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String appClientId() {
        return  "las:app-client:" + hexUuid();
    }

    public static String assetId() {
        return  "las:asset:" + hexUuid();
    }

    public static String consentId() {
        return  "las:consent:" + hexUuid();
    }

    public static String datasetId() {
        return  "las:dataset:" + hexUuid();
    }

    public static String documentId() {
        return  "las:document:" + hexUuid();
    }

    public static String modelId() {
        return  "las:model:" + hexUuid();
    }

    public static String logId() {
        return  "las:log:" + hexUuid();
    }

    public static String secretId() {
        return  "las:secret:" + hexUuid();
    }

    public static String userId() {
        return  "las:user:" + hexUuid();
    }

    public static String transitionId() {
        return  "las:transition:" + hexUuid();
    }

    public static String workflowId() {
        return  "las:workflow:" + hexUuid();
    }

    public static String transitionExecutionId() {
        return  "las:transition-execution:" + hexUuid();
    }

    public static String workflowExecutionId() {
        return  "las:workflow-execution:" + hexUuid();
    }

    public static JSONObject schema() {
        return new JSONObject() {{
            put("$schema", "https://json-schema.org/draft-04/schema#");
        }};
    }

    public static JSONObject credentials() {
        return new JSONObject() {{
            put("username", hexUuid());
            put("password", hexUuid());
        }};
    }

    public static byte[] content() {
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        return b;
    }

    public static String email() {
        return "foo@example.com";
    }

    public static String avatar() {
        return "jpeg-string";
    }

    public static String isoDateTime() {
        return ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
    }

    public static ZonedDateTime dateTime() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

    public static String dockerImageUrl() {
        return "my/docker:image";
    }
}
