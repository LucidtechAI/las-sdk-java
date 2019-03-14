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

public class Main {
   public static void main(String[] args) throws IOException, URISyntaxException {
       Client client = new Client("<api endpoint>");
       
       // Get document handle
       JSONObject postDocumentsResponse = this.client.postDocuments("image/jpeg", "foobar");
       
       // Put document to s3
       URI uploadUri = new URI(postDocumentsResponse.getString("uploadUrl"));
       String documentId = postDocumentsResponse.getString("documentId");
       this.client.putDocument("document.jpeg", "image/jpeg", uploadUri);
       
       // Get prediction on document
       JSONObject prediction = this.client.postPredictions(documentId, "invoice");
   }
}
```

## Contributing

### Prerequisites

* Gradle

#### Arch

```bash
$ pacman -S gradle
```

### Run tests

```bash
$ gradle test
```
