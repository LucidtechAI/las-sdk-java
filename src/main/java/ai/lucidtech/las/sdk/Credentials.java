package ai.lucidtech.las.sdk;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Credentials {
    private String accessKeyId;
    private String secretAccessKey;
    private String apiKey;

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
     *  Uses default location ~/.lucidtech/credentials.cfg to fetch credentials
     *
     * @param credentialsPath Path to credentials file
     */
    public Credentials(String credentialsPath) {
        this.readCredentials(credentialsPath);
    }

    /**
     *  Used to fetch and store credentials.
     *
     * @param accessKeyId Access Key Id
     * @param secretAccessKey Secret Access Key
     * @param apiKey API key
     */
    public Credentials(String accessKeyId, String secretAccessKey, String apiKey) {
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.apiKey = apiKey;
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
