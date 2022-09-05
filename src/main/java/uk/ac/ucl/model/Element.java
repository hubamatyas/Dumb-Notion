package uk.ac.ucl.model;

import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public abstract class Element {
    private String type;
    private String body;

    public Element(String type, String body)
    {
        this.type = type;
        this.body = body;
    }

    public String getBody()
    {
        return body;
    }
    public String getType()
    {
        return type;
    }
    public abstract void save(JSONObject noteObject) throws FileNotFoundException;
    public abstract ArrayList<String> load() throws FileNotFoundException;
}
