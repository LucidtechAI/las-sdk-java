package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreatePredictionOptions extends Options {
    private Integer maxPages;
    private Boolean autoRotate;
    private ImageQuality imageQuality;

    public CreatePredictionOptions setMaxPages(int maxPages) {
        this.maxPages = maxPages;
        return this;
    }

    public CreatePredictionOptions setAutoRotate(boolean autoRotate) {
        this.autoRotate = autoRotate;
        return this;
    }

    public CreatePredictionOptions setImageQuality(ImageQuality imageQuality) {
        this.imageQuality = imageQuality;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(body, "maxPages", this.maxPages);
        this.addOption(body, "autoRotate", this.autoRotate);
        this.addOption(body, "imageQuality", this.imageQuality.value);
        return body;
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
