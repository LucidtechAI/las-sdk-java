package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreatePredictionOptions {
    private Integer maxPages;
    private Boolean autoRotate;
    private ImageQuality imageQuality;


    public CreatePredictionOptions() {
        this.maxPages = null;
        this.autoRotate = null;
    }

    public CreatePredictionOptions(int maxPages, boolean autoRotate) {
        this.maxPages = maxPages;
        this.autoRotate = autoRotate;
    }

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
        if (this.maxPages != null) {
            body.put("maxPages", this.maxPages);
        }
        if (this.autoRotate != null) {
            body.put("autoRotate", this.autoRotate);
        }
        if (this.imageQuality != null) {
            body.put("imageQuality", this.imageQuality.value);
        }
        return body;
    }

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
