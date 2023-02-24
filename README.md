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
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import ai.lucidtech.las.sdk.Client;
import ai.lucidtech.las.sdk.ContentType;
import ai.lucidtech.las.sdk.Credentials;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        // TODO enter values from your API Key:
        Credentials credentials = new Credentials(
            clientId,
            clientSecret,
            authEndpoint,
            apiEndpoint
        );
        // TODO change values:
        String modelName = "las:organization:cradl/las:model:invoice";
        String pdf = "<filename>.pdf";
        
        Client client = new Client(credentials);
        JSONObject document = client.createDocument(Files.readAllBytes(Paths.get(pdf)), ContentType.PDF);
        String documentId = document.getString("documentId");
        JSONObject prediction = this.createPrediction(documentId, modelName);
        JSONArray fields = (JSONArray) prediction.get("predictions");
        fields.forEach(item -> {
            JSONObject f = (JSONObject) item;
            System.out.println(f.getString("label") + " = " + f.getString("value") + " (" + f.getFloat("confidence") + ")");
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
- TEST_LAS_AUTH_ENDPOINT
- TEST_LAS_API_ENDPOINT
```bash
$ gradle test
```
### Use linter

```bash
$ pmd -d . -R ruleset.xml 
```

## Publishing

### Prerequisites

* Gradle [help](https://docs.gradle.org/current/userguide/userguide.html)
* PGP key [help](https://central.sonatype.org/pages/working-with-pgp-signatures.html)
* gradle.properties [help](https://central.sonatype.org/pages/gradle.html)
* GRADLE_USER_HOME defined to point at directory containing above file
* Adjust version in gradle.properties in project root
* Use suffix "-SNAPSHOT" in version to test publish before publishing to production repo. (0.0.1-SNAPSHOT etc)

### Usage

```sh
$ make publish
```
