package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class AppClientsTest extends ClientTest {
    private void assertAppClient(JSONObject appClient) throws IOException {
        Assert.assertTrue(appClient.has("apiKey"));
        Assert.assertTrue(appClient.has("appClientId"));
        Assert.assertTrue(appClient.has("clientId"));
        Assert.assertTrue(appClient.has("createdTime"));
        Assert.assertTrue(appClient.has("description"));
        Assert.assertTrue(appClient.has("hasSecret"));
        Assert.assertTrue(appClient.has("name"));
    }

    private void assertAppClients(JSONObject appClients) throws IOException {
        Assert.assertTrue(appClients.has("appClients"));
        Assert.assertTrue(appClients.has("nextToken"));

        for (Object appClient: appClients.getJSONArray("appClients")) {
            this.assertAppClient((JSONObject) appClient);
        }
    }

    @Test
    public void testCreateAppClient() throws IOException, APIException, MissingAccessTokenException {
        JSONObject appClient = this.client.createAppClient();
        this.assertAppClient(appClient);
    }

    @Test
    public void testCreateAppClientWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateAppClientOptions options = new CreateAppClientOptions()
            .setName("AppClient Name")
            .setDescription("AppClient Description")
            .setGenerateSecret(false)
            .setCallbackUrls(new String[] {"https://example.org/authCallback"})
            .setLogoutUrls(new String[] {"https://example.org/logout"});
        JSONObject appClient = this.client.createAppClient(options);
        this.assertAppClient(appClient);
    }

    @Test
    public void testListAppClients() throws IOException, APIException, MissingAccessTokenException {
        JSONObject appClients = this.client.listAppClients();
        this.assertAppClients(appClients);
    }

    @Test
    public void testListAppClientsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListAppClientsOptions options = new ListAppClientsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject appClients = this.client.listAppClients(options);
        this.assertAppClients(appClients);
    }

    @Test
    public void testDeleteAppClient() throws IOException, APIException, MissingAccessTokenException {
        String appClientId = TestUtils.appClientId();
        JSONObject appClient = this.client.deleteAppClient(appClientId);
        this.assertAppClient(appClient);
    }
}
