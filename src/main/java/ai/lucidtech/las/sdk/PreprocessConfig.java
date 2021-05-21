package ai.lucidtech.las.sdk;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;


public class PreprocessConfig extends Options {
    private ImageQuality imageQuality;
    private Boolean autoRotate;
    private Integer maxPages;

    public PreprocessConfig setImageQuality(ImageQuality imageQuality) {
        this.imageQuality = imageQuality;
        return this;
    }

    public PreprocessConfig setAutoRotate(Boolean autoRotate) {
        this.autoRotate = autoRotate;
        return this;
    }

    public PreprocessConfig setMaxPages(Integer maxPages) {
        this.maxPages = maxPages;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        if (this.imageQuality != null) {
            this.addOption(body, "imageQuality", this.imageQuality.value);
        }
        this.addOption(body, "autoRotate", this.autoRotate);
        this.addOption(body, "maxPages", this.maxPages);
        return body;
    }
}
