package uk.ac.ucl.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Note {
    private int id;
    private String title;
    private String path = "./data/note";
    private JSONObject noteObject = new JSONObject();
    private final ArrayList<Element> noteElements = new ArrayList<>();

    public Note(int id, String title) throws IOException {
        this.id = id;
        this.title = title;
        path += (id + ".json");

        noteObject.put("id", id);
        noteObject.put("title", title);
        noteObject.put("elements", new JSONArray());
        save();
    }

    public void save() throws IOException
    {
        Files.write(Path.of(path), noteObject.toJSONString().getBytes());
    }

    public void addElement(Element noteElement)
    {
        noteElements.add(noteElement);
    }

    public JSONObject getNoteObject()
    {
        return noteObject;
    }

    public String getTitle()
    {
        return title;
    }

    public int getId()
    {
        return id;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public void setNoteObject(JSONObject noteObject)
    {
        this.noteObject = noteObject;
    }

    public ArrayList<Element> getNoteElements()
    {
        return noteElements;
    }
}

