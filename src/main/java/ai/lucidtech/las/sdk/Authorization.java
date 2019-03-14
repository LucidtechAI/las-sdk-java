package ai.lucidtech.las.sdk;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


class Authorization {
    private static final String ALGORITHM = "AWS4-HMAC-SHA256";

    private String region;
    private String service;
    private Credentials credentials;

    Authorization(Credentials credentials) {
        this.region = "eu-west-1";
        this.service = "execute-api";
        this.credentials = credentials;
    }

    Map<String, String> signHeaders(URI uri, String method, byte[] body) {
        if (body == null) {
            body = "".getBytes();
        }

        Instant now = Instant.now();
        Map<String, String> canonicalHeaders = getCanonicalHeaders(uri, now);

        String canonicalRequest = this.getCanonicalRequest(uri, method, body, canonicalHeaders);
        String requestDigest = DigestUtils.sha256Hex(canonicalRequest.getBytes());
        String credentialScope = this.getCredentialScope(now);
        byte[] stringToSign = this.getSignString(now, credentialScope, requestDigest);
        byte[] signatureKey = this.getSignatureKey(now);

        String signature = Hex.encodeHexString(this.hmacSHA256(signatureKey, stringToSign));
        return buildAuthHeader(now, credentialScope, canonicalHeaders.keySet(), signature);
    }

    private byte[] hmacSHA256(byte[] key, byte[] message) {
        return HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, key).doFinal(message);
    }

    private byte[] getSignString(Instant now, String credentialScope, String requestDigest) {
        List<String> signParts = Arrays.asList(Authorization.ALGORITHM, this.getDateTimeStamp(now),
                credentialScope, requestDigest);
        return String.join("\n", signParts).getBytes();
    }

    private String getCredentialScope(Instant now) {
        List<String> scopeParts = Arrays.asList(this.getDateStamp(now), this.region, this.service, "aws4_request");
        return String.join("/", scopeParts);
    }

    private Map<String, String> getCanonicalHeaders(URI uri, Instant now) {
        Map<String, String> headers = new TreeMap<>();
        headers.put("host", uri.getHost());
        headers.put("x-amz-date", this.getDateTimeStamp(now));
        headers.put("x-api-key", this.credentials.getApiKey());

        return headers;
    }

    private static String toHeaderPart(Map.Entry<String, String> header) {
        return header.getKey() + ":" + header.getValue() + "\n";
    }

    private String getCanonicalRequest(URI uri, String method, byte[] body, Map<String, String> canonicalHeaders) {
        String canonicalQueryString = this.getCanonicalQueryString(uri.getQuery());
        List<String> headerPartList = canonicalHeaders.entrySet().stream()
                .map(Authorization::toHeaderPart)
                .collect(Collectors.toList());

        String headerParts = String.join("", headerPartList);
        String headerKeys = String.join(";", canonicalHeaders.keySet());
        String payloadHash = DigestUtils.sha256Hex(body);

        List<String> requestComponents = Arrays.asList(method, uri.getPath(), canonicalQueryString, headerParts,
                headerKeys, payloadHash);
        return String.join("\n", requestComponents);
    }

    private String getCanonicalQueryString(String queryString) {
        // TODO: Fixme
        return "";
    }

    private byte[] getSignatureKey(Instant now) {
        byte[] signatureKey = ("AWS4" + this.credentials.getSecretAccessKey()).getBytes();
        List<String> parts = Arrays.asList(this.getDateStamp(now), this.region, this.service, "aws4_request");
        for (String part : parts) {
            signatureKey = this.hmacSHA256(signatureKey, part.getBytes());
        }

        return signatureKey;
    }

    private static String toAuthPart(Map.Entry<String, String> auth) {
        return auth.getKey() + "=" + auth.getValue();
    }

    private Map<String, String> buildAuthHeader(Instant now, String credentialScope, Iterable<String> signedHeaders,
                                                String signature) {
        Map<String, String> auth = new TreeMap<>();
        auth.put("Credential", this.credentials.getAccessKeyId() + "/" + credentialScope);
        auth.put("SignedHeaders", String.join(";", signedHeaders));
        auth.put("Signature", signature);

        List<String> authParts = auth.entrySet().stream().map(Authorization::toAuthPart).collect(Collectors.toList());
        String authString = String.join(", ", authParts);

        Map<String, String> headers = new TreeMap<>();
        headers.put("x-amz-date", this.getDateTimeStamp(now));
        headers.put("x-api-key", this.credentials.getApiKey());
        headers.put("Authorization", Authorization.ALGORITHM + " " + authString);
        return headers;
    }

    private String getDateTimeStamp(Instant now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
                .withZone(ZoneId.of("Z"));
        return formatter.format(now);
    }

    private String getDateStamp(Instant now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                .withZone(ZoneId.of("Z"));
        return formatter.format(now);
    }
}
