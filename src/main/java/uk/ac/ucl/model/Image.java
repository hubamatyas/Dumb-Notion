package uk.ac.ucl.model;

import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Image extends Element
{

    private String imagePath;

    public Image(String path)
    {
        super("image", path);
        // path should be a base64 encode
    }

    @Override
    public void save(JSONObject noteObject) throws FileNotFoundException
    {


    }

    @Override
    public ArrayList<String> load()
    {
        ArrayList<String> image = new ArrayList<>();
        return image;
    }
}
