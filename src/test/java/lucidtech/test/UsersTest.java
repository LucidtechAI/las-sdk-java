package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;


public class UsersTest extends ClientTest {

    private void assertUser(JSONObject user) throws IOException {
        Assert.assertTrue(user.has("userId"));
        Assert.assertTrue(user.has("email"));
        Assert.assertTrue(user.has("name"));
        Assert.assertTrue(user.has("avatar"));
    }

    @Test
    public void testCreateUser() throws IOException, APIException, MissingAccessTokenException {
        JSONObject user = this.client.createUser(this.service.email());
        this.assertUser(user);
    }

    @Test
    public void testCreateUserWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateUserOptions options = new CreateUserOptions().setName("foo").setAvatar(this.service.avatar());
        JSONObject user = this.client.createUser(this.service.email(), options);
        this.assertUser(user);
    }

    @Test
    public void testCreateUserWithAvatarBytes() throws IOException, APIException, MissingAccessTokenException {
        CreateUserOptions options = new CreateUserOptions().setName("foo").setAvatar(this.service.content());
        JSONObject user = this.client.createUser(this.service.email(), options);
        this.assertUser(user);

    }

    @Test
    public void testListUsers() throws IOException, APIException, MissingAccessTokenException {
        JSONObject response = this.client.listUsers();
        JSONArray users = response.getJSONArray("users");
        Assert.assertNotNull(users);
    }

    @Test
    public void testListUsersWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListUsersOptions options = new ListUsersOptions().setMaxResults(30).setNextToken("foo");
        JSONObject response = this.client.listUsers(options);
        JSONArray users = response.getJSONArray("users");
        Assert.assertNotNull(users);
    }

    @Test
    public void testGetUser() throws IOException, APIException, MissingAccessTokenException {
        JSONObject user = this.client.getUser(this.service.userId());
        this.assertUser(user);
    }

    @Test
    public void testUpdateUser() throws IOException, APIException, MissingAccessTokenException {
        UpdateUserOptions options = new UpdateUserOptions()
        .setName("foo")
        .setAvatar(this.service.avatar());
        JSONObject user = this.client.updateUser(this.service.userId(), options);
        this.assertUser(user);
    }
}
