package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import java.util.UUID;
import java.util.Random;

import org.json.JSONObject;

public class Service {

    public String hexUuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String assetId(){
        return  "las:asset:" + this.hexUuid();
    }

    public String batchId(){
        return  "las:batch:" + this.hexUuid();
    }

    public String consentId(){
        return  "las:consent:" + this.hexUuid();
    }

    public String documentId(){
        return  "las:document:" + this.hexUuid();
    }

    public String modelId(){
        return  "las:model:" + this.hexUuid();
    }

    public String logId(){
        return  "las:log:" + this.hexUuid();
    }

    public String secretId(){
        return  "las:secret:" + this.hexUuid();
    }

    public String userId(){
        return  "las:user:" + this.hexUuid();
    }

    public String transitionId(){
        return  "las:transition:" + this.hexUuid();
    }

    public String workflowId(){
        return  "las:workflow:" + this.hexUuid();
    }

    public String transitionExecutionId(){
        return  "las:transition-execution:" + this.hexUuid();
    }

    public String workflowExecutionId(){
        return  "las:workflow-execution:" + this.hexUuid();
    }

    public JSONObject schema(){
        return new JSONObject(){{
            put("$schema", "https://json-schema.org/draft-04/schema#");
        }};
    }

    public JSONObject credentials(){
        return new JSONObject(){{
            put("username", hexUuid());
            put("password", hexUuid());
        }};
    }

    public byte[] content(){
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        return b;
    }
}
