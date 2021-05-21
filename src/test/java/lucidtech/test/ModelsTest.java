package lucidtech.test;

import ai.lucidtech.las.sdk.*;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class ModelsTest extends ClientTest {
    private void assertModel(JSONObject model) throws IOException {
        Assert.assertTrue(model.has("createdTime"));
        Assert.assertTrue(model.has("description"));
        Assert.assertTrue(model.has("fieldConfig"));
        Assert.assertTrue(model.has("height"));
        Assert.assertTrue(model.has("modelId"));
        Assert.assertTrue(model.has("name"));
        Assert.assertTrue(model.has("preprocessConfig"));
        Assert.assertTrue(model.has("status"));
        Assert.assertTrue(model.has("updatedTime"));
        Assert.assertTrue(model.has("width"));
    }

    private void assertModels(JSONObject models) throws IOException {
        Assert.assertTrue(models.has("models"));
        Assert.assertTrue(models.has("nextToken"));

        for (Object model: models.getJSONArray("models")) {
            this.assertModel((JSONObject) model);
        }
    }

    @Test
    public void testCreateModel() throws IOException, APIException, MissingAccessTokenException {
        int width = 800;
        int height = 800;
        FieldConfig fieldConfig = new FieldConfig()
            .addField(new Field("total_amount", FieldType.AMOUNT, 12).setDescription("Total Amount"))
            .addField(new Field("purchase_date", FieldType.DATE, 10).setDescription("Purchase Date"))
            .addField(new Field("supplier_id", FieldType.ALPHANUM, 20).setDescription("Supplier ID"));

        JSONObject model = this.client.createModel(width, height, fieldConfig);
        this.assertModel(model);
    }

    @Test
    public void testCreateModelWithOptions() throws IOException, APIException, MissingAccessTokenException {
        PreprocessConfig preprocessConfig = new PreprocessConfig()
            .setImageQuality(ImageQuality.HIGH)
            .setAutoRotate(true)
            .setMaxPages(3);

        CreateModelOptions options = new CreateModelOptions()
            .setName("Model Name")
            .setDescription("Model Description")
            .setPreprocessConfig(preprocessConfig);

        int width = 800;
        int height = 800;
        FieldConfig fieldConfig = new FieldConfig()
            .addField(new Field("total_amount", FieldType.AMOUNT, 12).setDescription("Total Amount"))
            .addField(new Field("purchase_date", FieldType.DATE, 10).setDescription("Purchase Date"))
            .addField(new Field("supplier_id", FieldType.ALPHANUM, 20).setDescription("Supplier ID"));

        JSONObject model = this.client.createModel(width, height, fieldConfig, options);
        this.assertModel(model);
    }

    @Test
    public void testGetModel() throws IOException, APIException, MissingAccessTokenException {
        String modelId = TestUtils.modelId();
        JSONObject model = this.client.getModel(modelId);
        this.assertModel(model);
    }

    @Test
    public void testUpdateModelWithOptions() throws IOException, APIException, MissingAccessTokenException {
        PreprocessConfig preprocessConfig = new PreprocessConfig()
            .setImageQuality(ImageQuality.HIGH)
            .setAutoRotate(true)
            .setMaxPages(3);

        FieldConfig fieldConfig = new FieldConfig()
            .addField(new Field("total_amount", FieldType.AMOUNT, 12).setDescription("Total Amount"))
            .addField(new Field("purchase_date", FieldType.DATE, 10).setDescription("Purchase Date"))
            .addField(new Field("supplier_id", FieldType.ALPHANUM, 20).setDescription("Supplier ID"));

        int width = 800;
        int height = 800;
        ModelStatus modelStatus = ModelStatus.TRAINING;

        UpdateModelOptions options = new UpdateModelOptions()
            .setName("Model Name")
            .setDescription("Model Description")
            .setWidth(width)
            .setHeight(height)
            .setModelStatus(modelStatus)
            .setFieldConfig(fieldConfig)
            .setPreprocessConfig(preprocessConfig);

        String modelId = TestUtils.modelId();
        JSONObject model = this.client.updateModel(modelId, options);
        this.assertModel(model);
    }

    @Test
    public void testListModels() throws IOException, APIException, MissingAccessTokenException {
        JSONObject models = this.client.listModels();
        this.assertModels(models);
    }

    @Test
    public void testListModelsWithOptions() throws IOException, APIException, MissingAccessTokenException {
        ListModelsOptions options = new ListModelsOptions()
            .setMaxResults(30)
            .setNextToken("Dummy NextToken");
        JSONObject models = this.client.listModels(options);
        this.assertModels(models);
    }
}
