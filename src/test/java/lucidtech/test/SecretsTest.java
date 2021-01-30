package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;


public class SecretsTest {

    private Client client;
    private Service service;

    @Before
    public void setUp() throws MissingCredentialsException {
        this.service = new Service();
        Credentials credentials = new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010");
        this.client = new Client(credentials);
    }

    private void assertSecret(JSONObject secret) throws IOException {
        Assert.assertTrue(secret.has("secretId"));
        Assert.assertTrue(secret.has("name"));
        Assert.assertTrue(secret.has("description"));
    }

    @Test
    public void testCreateSecret() throws IOException, APIException, MissingAccessTokenException {
        JSONObject secret = this.client.createSecret(this.service.credentials());
        this.assertSecret(secret);
    }

    @Test
    public void testCreateSecretWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateSecretOptions options = new CreateSecretOptions().setName("foo").setDescription("bar");
        JSONObject secret = this.client.createSecret(this.service.credentials(), options);
        this.assertSecret(secret);
    }

    @Test
    public void testListSecrets() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listSecrets();
        JSONArray secrets = response.getJSONArray("secrets");
        Assert.assertNotNull(secrets);
    }

    @Test
    public void testListSecretsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListSecretsOptions options = new ListSecretsOptions().setMaxResults(30).setNextToken("foo");
        JSONObject response = this.client.listSecrets(options);
        JSONArray secrets = response.getJSONArray("secrets");
        Assert.assertNotNull(secrets);
    }

    @Test
    public void testUpdateSecret() throws IOException, APIException, MissingAccessTokenException {
        UpdateSecretOptions options = new UpdateSecretOptions()
        .setName("foo")
        .setDescription("bar")
        .setData(this.service.credentials());
        JSONObject secret = this.client.updateSecret(this.service.secretId(), options);
        this.assertSecret(secret);
    }
}
