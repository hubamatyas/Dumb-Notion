package uk.ac.ucl.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Text extends Element
{

    public Text(String text) throws FileNotFoundException
    {
        super("text", text);
    }

    @Override
    public void save(JSONObject noteObject)
    {
        JSONObject textElement = new JSONObject();
        textElement.put("type", "text");
        textElement.put("body", super.getBody());
        JSONArray elementsArray = (JSONArray) noteObject.get("elements");
        elementsArray.add(textElement);
        noteObject.put("elements", elementsArray);
    }

    @Override
    public ArrayList<String> load() throws FileNotFoundException
    {
        String[] bodySplit = super.getBody().split("\n");
        return new ArrayList<>(Arrays.asList(bodySplit));
    }
}