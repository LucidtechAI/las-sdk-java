package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class LogsTest  extends ClientTest {

    private void assertLog(JSONObject log) throws IOException {
        Assert.assertTrue(log.has("logId"));
        Assert.assertTrue(log.has("transitionId"));
        Assert.assertTrue(log.has("events"));
    }

    @Test
    public void testGetLog() throws IOException, APIException, MissingAccessTokenException {
        JSONObject log = this.client.getLog(Service.logId());
        this.assertLog(log);
    }


}
