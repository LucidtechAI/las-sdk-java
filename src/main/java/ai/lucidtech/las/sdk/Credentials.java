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

    private void readCredentialsFromEnviron() {
        Map<String, String> pairs = new HashMap<String, String>();
        pairs.put("clientId", "LAS_CLIENT_ID");
        pairs.put("clientSecret", "LAS_CLIENT_SECRET");
        pairs.put("apiKey", "LAS_API_KEY");
        pairs.put("authEndpoint", "LAS_AUTH_ENDPOINT");
        pairs.put("apiEndpoint", "LAS_API_ENDPOINT");

        Map<String, String> result = new HashMap<String, String>();

        for (Map.Entry<String, String> entry : pairs.entrySet()) {
            try {
                result.put(entry.getKey(), System.getenv(entry.getValue()));
            } catch (NullPointerException) {

            }
        }

        String clientId = System.getenv("LAS_CLIENT_ID");
        String clientSecret = System.getenv("LAS_CLIENT_SECRET");
        String apiKey = System.getenv("LAS_API_KEY");
        String authEndpoint = System.getenv("LAS_AUTH_ENDPOINT");
        String apiEndpoint = System.getenv("LAS_API_ENDPOINT");
    }

    private void readCredentials(String credentialsPath) {
        // TODO Read INI files properly
        try(FileInputStream input = new FileInputStream(credentialsPath)) {
            Properties properties = new Properties();
            properties.load(input);

            this.accessKeyId = properties.getProperty("access_key_id");
            this.secretAccessKey = properties.getProperty("secret_access_key");
            this.apiKey = properties.getProperty("api_key");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        var env = System.getenv();
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public String getSecretAccessKey() {
        return this.secretAccessKey;
    }

    public String getApiKey() {
        return this.apiKey;
    }
}
