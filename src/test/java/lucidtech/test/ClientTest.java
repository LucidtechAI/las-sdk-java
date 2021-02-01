package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.powermock.api.mockito.PowerMockito;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


public class ClientTest {

    protected Client client;
    protected Service service;

    @Before
    public void setUp() throws MissingCredentialsException, MissingAccessTokenException {
        this.service = new Service();
        Credentials mock = mock(Credentials.class);
        Credentials credentials = spy(new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010"));
        when(credentials.getAccessToken(any(HttpClient.class))).thenReturn("foo");
        //Credentials credentials = new Credentials("test", "test", "test", "test", "http://127.0.0.1:4010");
        this.client = new Client(credentials);
        HttpClient httpClient = HttpClientBuilder.create().build();
        System.out.println("getAccessToken: " + credentials.getAccessToken(httpClient));
        //System.out.println("getAccessToken: " + client.credentials.getAccessToken(httpClient));

    }
}

