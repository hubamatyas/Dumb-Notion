package uk.ac.ucl.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Url extends Element
{

    private String urlPath = "/url0.txt";

    public Url(String url) throws FileNotFoundException
    {
        super("url", url);
    }
    @Override
    public void save(JSONObject noteObject) throws FileNotFoundException
    {
        JSONObject urlElement = new JSONObject();
        urlElement.put("type", "url");
        urlElement.put("body", super.getBody());
        JSONArray elementsArray = (JSONArray) noteObject.get("elements");
        elementsArray.add(urlElement);
        noteObject.put("elements", elementsArray);
    }

    @Override
    public ArrayList<String> load() throws FileNotFoundException
    {
        ArrayList<String> url = new ArrayList<>();
        url.add(super.getBody());
        return url;
    }
}
