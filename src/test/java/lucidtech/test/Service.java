package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.util.Properties;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.StreamSupport;


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
        return  "las:transitionExecution:" + this.hexUuid();
    }

    public String workflowExecutionId(){
        return  "las:workflowExecution:" + this.hexUuid();
    }
}
