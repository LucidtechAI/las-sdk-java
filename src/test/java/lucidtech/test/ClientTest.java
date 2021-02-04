package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.apache.http.client.HttpClient;

import org.junit.Before;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;


public class ClientTest {

    protected Client client;

    @Before
    public void setUp() throws MissingCredentialsException, MissingAccessTokenException {
        Credentials credentials = new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010");
        Credentials spyCredentials = spy(credentials);
        doReturn("foo").when(spyCredentials).getAccessToken(any(HttpClient.class));
        this.client = new Client(spyCredentials);

    }
}

