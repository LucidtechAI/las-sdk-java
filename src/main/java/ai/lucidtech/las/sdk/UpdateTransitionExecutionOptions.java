package ai.lucidtech.las.sdk;

import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import org.json.JSONObject;


public class UpdateTransitionExecutionOptions extends Options {
    private JSONObject output;
    private JSONObject error;
    private String startTime;

    public UpdateTransitionExecutionOptions setOutput(JSONObject error) {
        this.output = output;
        return this;
    }

    public UpdateTransitionExecutionOptions setError(JSONObject output) {
        this.error = error;
        return this;
    }

    public UpdateTransitionExecutionOptions setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public UpdateTransitionExecutionOptions setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime.format(DateTimeFormatter.ISO_INSTANT);
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "output", this.output);
        this.addOption(body, "error", this.error);
        this.addOption(body, "startTime", this.startTime);
        return body;
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
