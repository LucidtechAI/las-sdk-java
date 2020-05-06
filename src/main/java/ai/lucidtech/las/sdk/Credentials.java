package ai.lucidtech.las.sdk;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.time.Instant;
import java.util.Base64;

import org.apache.http.*;
import org.apache.http.client.AuthCache;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class Credentials {
    private String clientId;
    private String clientSecret;
    private String apiKey;
    private String authEndpoint;
    private String apiEndpoint;
    private String accessToken;
    private long expires;

    /**
     *  Used to fetch and store credentials.
     *  Uses default location ~/.lucidtech/credentials.cfg to fetch credentials
     */
    public Credentials() {
        String homeDir = System.getProperty("user.home");
        String credentialsPath = homeDir + "/.lucidtech/credentials.cfg";
        this.readFromFile(credentialsPath);
    }

    /**
     *  Used to fetch and store credentials.
     *
     * @param credentialsPath Path to credentials file
     */
    public Credentials(String credentialsPath) {
        this.readFromFile(credentialsPath);
    }

    /**
     *  Used to fetch and store credentials.
     *
     * @param clientId Client id
     * @param clientSecret Client secret
     * @param apiKey API key
     * @param authEndpoint Auth endpoint
     * @param apiEndpoint Domain endpoint of the api, e.g. https://<prefix>.api.lucidtech.ai/<version>
     */
    public Credentials(String clientId, String clientSecret, String apiKey, String authEndpoint, String apiEndpoint) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.apiKey = apiKey;
        this.authEndpoint = authEndpoint;
        this.apiEndpoint = apiEndpoint;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    private Map readFromEnviron() {
        Map<String, String> pairs = new HashMap<String, String>();
        pairs.put("clientId", "LAS_CLIENT_ID");
        pairs.put("clientSecret", "LAS_CLIENT_SECRET");
        pairs.put("apiKey", "LAS_API_KEY");
        pairs.put("authEndpoint", "LAS_AUTH_ENDPOINT");
        pairs.put("apiEndpoint", "LAS_API_ENDPOINT");

        Map<String, String> result = new HashMap<String, String>();

        for (Map.Entry<String, String> entry : pairs.entrySet()) {
            result.put(entry.getKey(), System.getenv(entry.getValue()));
        }

        return result;
    }

    private void readFromFile(String credentialsPath) {
        // TODO Read INI files properly
        try(FileInputStream input = new FileInputStream(credentialsPath)) {
            Properties properties = new Properties();
            properties.load(input);
            Map<String, String> credentialsFromEnviron = this.readFromEnviron();

            this.clientId = properties.getProperty("client_id");
            this.clientSecret = properties.getProperty("client_secret");
            this.apiKey = properties.getProperty("api_key");
            this.apiEndpoint = properties.getProperty("api_endpoint");
            this.authEndpoint = properties.getProperty("auth_endpoint");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private JSONObject getClientCredentials(HttpClient httpClient) throws IOException {
        HttpHost targetHost = new HttpHost("https://" + this.authEndpoint + "/oauth2/token?grant_type=client_credentials");

        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        HttpUriRequest request = new HttpPost("https://" + this.authEndpoint + "/oauth2/token?grant_type=client_credentials");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addHeader("Accept", "application/json");

        String authString = this.clientId + ":" + this.clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
        request.addHeader("Authorization", "Basic " + encodedAuth);

        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();
        int status = response.getStatusLine().getStatusCode();

        if (status != 200) {
            System.out.println("Request headers: ");
            printHeaders(request.getAllHeaders());
            System.out.println();
            System.out.println("Response headers: ");
            printHeaders(response.getAllHeaders());

            throw new RuntimeException("Failed to fetch access token: HTTP response code " + status);
        }

        String body = EntityUtils.toString(responseEntity);
        JSONObject jsonResponse = new JSONObject(body);

        if (!jsonResponse.has("access_token") || !jsonResponse.has("expires_in")) {
            throw new RuntimeException("Failed to fetch access token: invalid response body");
        }

        return jsonResponse;
    }

    void printHeaders(Header[] headers) {
        for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i].getName() + ": " + headers[i].getValue());
        }
    }

    public String getAccessToken(HttpClient httpClient) {
        if (accessToken == null || accessToken.isEmpty() || expires < Instant.now().getEpochSecond()) {
            try {
                JSONObject tokenData = this.getClientCredentials(httpClient);
                this.accessToken = tokenData.getString("access_token");
                this.expires = Instant.now().getEpochSecond() + tokenData.getInt("expires_in");
            } catch (IOException | RuntimeException ex) {
                ex.printStackTrace();
            }
        }

        return this.accessToken;
    }
}
