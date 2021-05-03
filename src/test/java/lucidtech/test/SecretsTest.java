package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class SecretsTest extends ClientTest {
    private void assertSecret(JSONObject secret) throws IOException {
        Assert.assertTrue(secret.has("description"));
        Assert.assertTrue(secret.has("name"));
        Assert.assertTrue(secret.has("secretId"));
    }

    private void assertSecrets(JSONObject secrets) throws IOException {
        Assert.assertTrue(secrets.has("nextToken"));
        Assert.assertTrue(secrets.has("secrets"));

        for (Object secret: secrets.getJSONArray("secrets")) {
            this.assertSecret((JSONObject) secret);
        }
    }

    @Test
    public void testCreateSecret() throws IOException, APIException, MissingAccessTokenException {
        JSONObject secret = this.client.createSecret(TestUtils.credentials());
        this.assertSecret(secret);
    }

    @Test
    public void testCreateSecretWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateSecretOptions options = new CreateSecretOptions()
            .setName("Secret Name")
            .setDescription("Secret Description");
        JSONObject secret = this.client.createSecret(TestUtils.credentials(), options);
        this.assertSecret(secret);
    }

    @Test
    public void testListSecrets() throws IOException, APIException, MissingAccessTokenException {
        JSONObject secrets = this.client.listSecrets();
        this.assertSecrets(secrets);
    }

    @Test
    public void testListSecretsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListSecretsOptions options = new ListSecretsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject secrets = this.client.listSecrets(options);
        this.assertSecrets(secrets);
    }

    @Test
    public void testUpdateSecret() throws IOException, APIException, MissingAccessTokenException {
        UpdateSecretOptions options = new UpdateSecretOptions()
            .setName("Secret Name")
            .setDescription("Secret Description")
            .setData(TestUtils.credentials());
        JSONObject secret = this.client.updateSecret(TestUtils.secretId(), options);
        this.assertSecret(secret);
    }

    @Test
    public void testDeleteSecret() throws IOException, APIException, MissingAccessTokenException {
        String secretId = TestUtils.secretId();
        JSONObject secret = this.client.deleteSecret(secretId);
        this.assertSecret(secret);
    }
}
