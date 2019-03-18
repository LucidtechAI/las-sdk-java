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
import ai.lucidtech.las.sdk.ApiClient;
import ai.lucidtech.las.sdk.Prediction;
import ai.lucidtech.las.sdk.Field;

import java.util.List;

public class Main {
   public static void main(String[] args) throws IOException, URISyntaxException {
       ApiClient apiClient = new ApiClient("<api endpoint>");
       Prediction prediction = apiClient.predict("document.jpeg", "invoice");
       
       List<Field> fields = prediction.getFields();
       for (Field field : fields) {
           label = field.getLabel();
           value = field.getValue();
           confidence = field.getConfidence();
           
           System.out.println("label: " + label + " value: " + value + " conf: " + confidence);
       }
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
