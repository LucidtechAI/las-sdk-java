package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class LogsTest {

    private Client client;
    private Service service;

    @Before
    public void setUp() throws MissingCredentialsException {
        Credentials credentials = new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010");
        this.client = new Client(credentials);
        this.service = new Service();
    }

    private void assertLog(JSONObject log) throws IOException {
        Assert.assertTrue(log.has("logId"));
        Assert.assertTrue(log.has("transitionId"));
        Assert.assertTrue(log.has("events"));
    }

    @Test
    public void testGetLog() throws IOException, APIException, MissingAccessTokenException {
        JSONObject log = this.client.getLog(this.service.logId());
        this.assertLog(log);
    }


}