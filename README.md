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
import ai.lucidtech.las.sdk.LasClient;
import ai.lucidtech.las.sdk.ContentType;

public class Main {
   public static void main(String[] args) throws IOException, URISyntaxException {
       LasClient lasClient = new LasClient("<api endpoint>");
       
       // Get document handle
       ContentType contentType = ContentType.JPEG;
       JSONObject postDocumentsResponse = lasClient.postDocuments(contentType, "foobar");
       
       // Put document to s3
       URI uploadUri = new URI(postDocumentsResponse.getString("uploadUrl"));
       String documentId = postDocumentsResponse.getString("documentId");
       lasClient.putDocument("document.jpeg", contentType, uploadUri);
       
       // Get prediction on document
       JSONObject prediction = lasClient.postPredictions(documentId, "invoice");
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

```bash
$ gradle test
```
