package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class UsersTest extends ClientTest {
    private void assertUser(JSONObject user) throws IOException {
        Assert.assertTrue(user.has("avatar"));
        Assert.assertTrue(user.has("email"));
        Assert.assertTrue(user.has("name"));
        Assert.assertTrue(user.has("userId"));
    }

    private void assertUsers(JSONObject users) throws IOException {
        Assert.assertTrue(users.has("nextToken"));
        Assert.assertTrue(users.has("users"));

        for (Object user: users.getJSONArray("users")) {
            this.assertUser((JSONObject) user);
        }
    }

    @Test
    public void testCreateUser() throws IOException, APIException, MissingAccessTokenException {
        JSONObject user = this.client.createUser(TestUtils.email());
        this.assertUser(user);
    }

    @Test
    public void testCreateUserWithOptions() throws IOException, APIException, MissingAccessTokenException {
        CreateUserOptions options = new CreateUserOptions()
            .setName("John Doe")
            .setAvatar(TestUtils.avatar());
        JSONObject user = this.client.createUser(TestUtils.email(), options);
        this.assertUser(user);
    }

    @Test
    public void testCreateUserWithAvatarBytes() throws IOException, APIException, MissingAccessTokenException {
        CreateUserOptions options = new CreateUserOptions()
            .setName("John Doe")
            .setAvatar(TestUtils.content());
        JSONObject user = this.client.createUser(TestUtils.email(), options);
        this.assertUser(user);
    }

    @Test
    public void testListUsers() throws IOException, APIException, MissingAccessTokenException {
        JSONObject users = this.client.listUsers();
        this.assertUsers(users);
    }

    @Test
    public void testListUsersWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListUsersOptions options = new ListUsersOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject users = this.client.listUsers(options);
        this.assertUsers(users);
    }

    @Test
    public void testGetUser() throws IOException, APIException, MissingAccessTokenException {
        JSONObject user = this.client.getUser(TestUtils.userId());
        this.assertUser(user);
    }

    @Test
    public void testUpdateUser() throws IOException, APIException, MissingAccessTokenException {
        UpdateUserOptions options = new UpdateUserOptions()
            .setName("John Doe")
            .setAvatar(TestUtils.avatar());
        JSONObject user = this.client.updateUser(TestUtils.userId(), options);
        this.assertUser(user);
    }
}
