package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class DeleteDocumentsOptions extends DeleteResourcesOptions<DeleteDocumentsOptions> {
    private String[] batchId;
    private String[] consentId;

    public DeleteDocumentsOptions setConsentId(String[] consentId) {
        this.consentId = consentId;
        return this;
    }

    public DeleteDocumentsOptions setBatchId(String[] batchId) {
        this.batchId = batchId;
        return this;
    }

    public List<NameValuePair> toList() {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        if (this.batchId != null) {
            for (String batchId: this.batchId) {
                parameters.add(new BasicNameValuePair("batchId", batchId));
            }
        }
        if (this.consentId != null) {
            for (String consentId: this.consentId) {
                parameters.add(new BasicNameValuePair("consentId", consentId));
            }
        }

        return super.addOptions(parameters);
    }
}
