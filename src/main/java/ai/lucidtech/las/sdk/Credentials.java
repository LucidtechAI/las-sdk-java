package ai.lucidtech.las.sdk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class Credentials {
    private String clientId;
    private String clientSecret;
    private String authEndpoint;
    private String apiEndpoint;
    private String accessToken;
    private long expires;

    /**
     *  Used to fetch and store credentials.
     *
     * @param clientId Client id
     * @param clientSecret Client secret
     * @param authEndpoint Auth endpoint
     * @param apiEndpoint Domain endpoint of the api, e.g. https://{prefix}.api.lucidtech.ai/{version}
     * @throws MissingCredentialsException Raised if some of credentials are missing
     */
    public Credentials(
        String clientId,
        String clientSecret,
        String authEndpoint,
        String apiEndpoint
    ) throws MissingCredentialsException {
        this.validateCredentials(clientId, clientSecret, authEndpoint, apiEndpoint);

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authEndpoint = authEndpoint;
        this.apiEndpoint = apiEndpoint;
        this.accessToken = null;
        this.expires = 0;
    }

    /**
     * @param httpClient Instance of HttpClient used to access the authentication endpoint
     * @return Access token, downloading it if necessary
     * @throws MissingAccessTokenException Raised if access token cannot be obtained
     */
    public String getAccessToken(HttpClient httpClient) throws MissingAccessTokenException {
        if (this.accessToken == null || this.accessToken.isEmpty() || this.expires < Instant.now().getEpochSecond()) {
            try {
                JSONObject tokenData = this.getClientCredentials(httpClient);
                this.accessToken = tokenData.getString("access_token");
                this.expires = Instant.now().getEpochSecond() + tokenData.getInt("expires_in");
            } catch (IOException | RuntimeException ex) {
                throw new MissingAccessTokenException();
            }
        }

        return this.accessToken;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    private void validateCredentials(String ...credentials) throws MissingCredentialsException {
        for (String value : credentials) {
            if (value == null) {
                throw new MissingCredentialsException();
            }
        }
    }

    private JSONObject getClientCredentials(HttpClient httpClient) throws IOException {
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
            throw new RuntimeException("Failed to fetch access token: HTTP response code " + status);
        }

        String body = EntityUtils.toString(responseEntity);
        JSONObject jsonResponse = new JSONObject(body);

        if (!jsonResponse.has("access_token") || !jsonResponse.has("expires_in")) {
            throw new RuntimeException("Failed to fetch access token: invalid response body");
        }

        return jsonResponse;
    }
}
