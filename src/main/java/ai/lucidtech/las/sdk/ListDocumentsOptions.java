package ai.lucidtech.las.sdk;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


public class ListDocumentsOptions extends ListResourcesOptions<ListDocumentsOptions> {
    private String batchId;
    private String consentId;

    public ListDocumentsOptions(){
        super();
        this.batchId = null;
        this.consentId = null;
    }

    public ListDocumentsOptions setConsentId(String consentId){
        this.consentId = consentId;
        return this;
    }

    public ListDocumentsOptions setBatchId(String batchId){
        this.batchId = batchId;
        return this;
    }

    public List<NameValuePair> toList(){
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if( this.batchId != null){
            parameters.add(new BasicNameValuePair("batchId", this.batchId));
        }
        if(this.consentId != null){
            parameters.add(new BasicNameValuePair("consentId", this.consentId));
        }
        return super.addOptions(parameters);
    }
}
