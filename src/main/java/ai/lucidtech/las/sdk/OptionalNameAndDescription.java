package ai.lucidtech.las.sdk;

import org.json.JSONObject;


public class OptionalNameAndDescription<T> {
    private String name;
    private String description;
    private static final String NOT_PROVIDED = "NOT PROVIDED";


    public OptionalNameAndDescription(){
        this.name = NOT_PROVIDED;
        this.description = NOT_PROVIDED;
    }

    public OptionalNameAndDescription(String name, String description){
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
}
