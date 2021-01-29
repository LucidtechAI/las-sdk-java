package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class NameAndDescriptionOptions<T> {
    private String name;
    private String description;
    private static final String NOT_PROVIDED = "NOT PROVIDED";


    public NameAndDescriptionOptions(){
        this.name = NOT_PROVIDED;
        this.description = NOT_PROVIDED;
    }

    public NameAndDescriptionOptions(String name, String description){
        this.name = name;
        this.description = description;
    }

    public T setName(String name){
        this.name = name;
        return (T) this;
    }

    public T setDescription(String description){
        this.description = description;
        return (T) this;
    }

    public JSONObject addOptions(JSONObject body){
        if (this.name != this.NOT_PROVIDED) {
            body.put("name", this.name);
        }
        if (this.description != this.NOT_PROVIDED) {
            body.put("description", this.description);
        }
        return body;
    }

    public JSONObject toJson(){
        JSONObject body = new JSONObject();
        return this.addOptions(body);
    }
}
