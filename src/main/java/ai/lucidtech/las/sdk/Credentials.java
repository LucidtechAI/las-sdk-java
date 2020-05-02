package ai.lucidtech.las.sdk;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.*;


public class Credentials {
    private String clientId;
    private String clientSecret;
    private String apiKey;
    private String authEndpoint;
    private String apiEndpoint;
    private String accessToken;
    private int expires;

    /**
     *  Used to fetch and store credentials.
     *  Uses default location ~/.lucidtech/credentials.cfg to fetch credentials
     */
    public Credentials() {
        String homeDir = System.getProperty("user.home");
        String credentialsPath = homeDir + "/.lucidtech/credentials.cfg";
        this.readCredentials(credentialsPath);
    }

    /**
     *  Used to fetch and store credentials.
     *
     * @param credentialsPath Path to credentials file
     */
    public Credentials(String credentialsPath) {
        this.readCredentials(credentialsPath);
    }

    /**
     *  Used to fetch and store credentials.
     *
     * @param clientId Client id
     * @param clientSecret Client secret
     * @param apiKey API key
     * @param authEndpoint Auth endpoint
     * @param apiEndpoint API endpoint
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

    private Map readCredentialsFromEnviron() {
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

    private void readCredentials(String credentialsPath) {
        // TODO Read INI files properly
        try(FileInputStream input = new FileInputStream(credentialsPath)) {
            Properties properties = new Properties();
            properties.load(input);

            this.clientId = properties.getProperty("client_id");
            this.clientSecret = properties.getProperty("client_secret");
            this.apiKey = properties.getProperty("api_key");
            this.apiEndpoint = properties.getProperty("api_endpoint");
            this.authEndpoint = properties.getProperty("auth_endpoint");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private List<?> getClientCredentials() {
        String url = "https://" + authEndpoint + "/token?grant_type=client_credentials";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
    }

    public String getAccessToken() {
        if (accessToken) {
            return accessToken;
        }
    }
}
