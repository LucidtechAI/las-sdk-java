# Java SDK for Lucidtech AI Services API

## Documentation

[Link to docs](https://docs.lucidtech.ai/java/v1/index.html)

## Usage

### Preconditions

- Documents must be in upright position
- Only one receipt or invoice per document is supported
- Supported file formats are: jpeg, pdf

### Quick start

```java
import ai.lucidtech.las.sdk.Client;
import ai.lucidtech.las.sdk.Prediction;
import ai.lucidtech.las.sdk.Field;

import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Credentials credentials = new Credentials(
            clientId,
            clientSecret,
            apiKey,
            authEndpoint,
            apiEndpoint
        );
        Client client = new Client("<api endpoint>");
        JSONObject document = this.createDocument(documentContent, contentType, consentId);
        String documentId = document.getString("documentId");
        JSONObject prediction = this.createPrediction(documentId, modelName);

        fields.forEach(item -> {
            JSONObject field = (JSONObject) item;
            System.out.println("field: " + field.getString("label"));
            System.out.println("field: " + field.getString("value"));
            System.out.println("field: " + field.getFloat("confidence"));
        });
    }
}
```

## Contributing

### Prerequisites

* Gradle
* Lucidtech AI Services credentials

#### Arch

```bash
$ pacman -S gradle
```

### Run tests
To run tests, the following environment variables should be present and contain valid AWS credentials:
- TEST_LAS_CLIENT_ID
- TEST_LAS_CLIENT_SECRET
- TEST_LAS_API_KEY
- TEST_LAS_AUTH_ENDPOINT
- TEST_LAS_API_ENDPOINT
```bash
$ gradle test
```

## Publishing

### Prerequisites

* Gradle
* PGP key
* gradle.properties [See link for required content of file](https://central.sonatype.org/pages/gradle.html)
* GRADLE_USER_HOME defined to point at directory containing above file
* Adjust version in gradle.properties in project root
* Use suffix "-SNAPSHOT" in version to test publish before publishing to production repo. (0.0.1-SNAPSHOT etc)

### Usage

```sh
$ make publish
```
