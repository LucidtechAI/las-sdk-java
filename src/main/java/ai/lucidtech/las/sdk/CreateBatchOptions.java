package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class CreateBatchOptions extends NameAndDescriptionOptions<CreateBatchOptions> {
    private Boolean containsPersonallyIdentifiableInformation;

    public CreateBatchOptions setContainsPersonallyIdentifiableInformation(
        Boolean containsPersonallyIdentifiableInformation
    ) {
        this.containsPersonallyIdentifiableInformation = containsPersonallyIdentifiableInformation;
        return this;
    }

    public JSONObject addOptions(JSONObject body) {
        this.addOption(
            body,
            "containsPersonallyIdentifiableInformation",
            this.containsPersonallyIdentifiableInformation
        );
        return super.addOptions(body);
    }
}
