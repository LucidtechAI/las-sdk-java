package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class SecretsTest extends ClientTest {

    private void assertSecret(JSONObject secret) throws IOException {
        Assert.assertTrue(secret.has("secretId"));
        Assert.assertTrue(secret.has("name"));
        Assert.assertTrue(secret.has("description"));
    }

    @Test
    public void testCreateSecret() throws IOException, APIException, MissingAccessTokenException {
        JSONObject secret = this.client.createSecret(Service.credentials());
        this.assertSecret(secret);
    }

    @Test
    public void testCreateSecretWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateSecretOptions options = new CreateSecretOptions().setName("foo").setDescription("bar");
        JSONObject secret = this.client.createSecret(Service.credentials(), options);
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
        .setData(Service.credentials());
        JSONObject secret = this.client.updateSecret(Service.secretId(), options);
        this.assertSecret(secret);
    }
}
