package uk.ac.ucl.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Model
{
    private int counter = 0;
    private final ArrayList<Note> notes = new ArrayList<>();
    private final String path = "./data";

    public Note getNoteById(int id)
    {
        for (Note note : notes) {
            int currenID = note.getId();
            if (currenID == id) {
                return note;
            }
        }
        return null;
    }

    public ArrayList<String> getNoteTitles(ArrayList<Integer> iDs)
    {
        ArrayList<String> titles = new ArrayList<>();
        for (Note note : notes)
        {
            if (iDs.contains(note.getId()))
            {
                titles.add(note.getTitle());
            }
        }
        return titles;
    }

    public String getNoteTitles(int id)
    {
        String title = "";
        for (Note note : notes)
        {
            if (id == note.getId())
            {
                title = note.getTitle();
            }
        }
        return title;
    }

    public ArrayList<Integer> getNoteIDs()
    {
        ArrayList<Integer> iDs = new ArrayList<>();
        for (Note note : notes) {
            iDs.add(note.getId());
        }
        return iDs;
    }

    public ArrayList<Integer> getNoteIDs(String searchWord)
    {
        ArrayList<Integer> iDs = new ArrayList<>();
        for (Note note : notes) {
            ArrayList<Element> noteElements = note.getNoteElements();
            if (note.getTitle().contains(searchWord))
            {
                iDs.add(note.getId());
            }
            for (Element noteElement : noteElements)
            {
                if (noteElement.getBody().contains(searchWord))
                {
                    iDs.add(note.getId());
                    break;
                }
            }
        }
        return iDs;
    }

    public void saveNote(int id, String title, ArrayList<Element> noteElements) throws IOException {
        Note toBeUpdatedNote = getNoteById(id);
        notes.remove(toBeUpdatedNote);
        Note updatedNote = new Note(id, title);
        JSONObject newNoteObject = updatedNote.getNoteObject();
        for (Element noteElement : noteElements)
        {
            noteElement.save(newNoteObject);
            updatedNote.addElement(noteElement);
        }
        updatedNote.save();
        notes.add(updatedNote);
    }

    public void addNote(String title, ArrayList<Element> noteElements) throws IOException
    {
        Note newNote = new Note(counter, title);
        JSONObject newNoteObject = newNote.getNoteObject();
        for (Element noteElement : noteElements)
        {
            noteElement.save(newNoteObject);
            newNote.addElement(noteElement);
        }
        newNote.save();
        notes.add(newNote);
        counter++;
    }

    public void archiveNote(int id) throws IOException {
        Note toBeArchived = getNoteById(id);
        JSONObject noteObject = new JSONObject();
        toBeArchived.setNoteObject(noteObject);
        toBeArchived.save();
        notes.remove(toBeArchived);
    }

    public void loadNotes() throws IOException, ParseException
    {
        File notesFolder = new File(path);
        String[] jsonFiles = notesFolder.list();
        for (int i = 0; i < Objects.requireNonNull(jsonFiles).length; i++)
        {
            if (jsonFiles[i].endsWith("json"))
            {
                JSONObject noteJSON = readJSON(notesFolder + "/" + jsonFiles[i]);
                if (noteJSON != null)
                {
                    createNoteFromJSON(noteJSON);
                }
            }
        }
    }

    private void createNoteFromJSON(JSONObject noteJSON) throws IOException {
        String title = (String) noteJSON.get("title");
        JSONArray jsonNoteElements = (JSONArray) noteJSON.get("elements");
        ArrayList<Element> noteElements = getElementsFromJSONArray(jsonNoteElements);
        if (noteElements != null)
        {
            addNote(title, noteElements);
        }
    }

    private ArrayList<Element> getElementsFromJSONArray(JSONArray jsonNoteElements) throws FileNotFoundException
    {
        ArrayList<Element> noteElements = new ArrayList<>();
        if (jsonNoteElements == null)
        {
            return null;
        }
        for (Object jsonNoteElement : jsonNoteElements) {
            JSONObject jsonElement = (JSONObject) jsonNoteElement;
            switch ((String) jsonElement.get("type")) {
                case "text" -> {
                    Element textElement = new Text((String) jsonElement.get("body"));
                    noteElements.add(textElement);
                }
                case "url" -> {
                    Element urlElement = new Url((String) jsonElement.get("body"));
                    noteElements.add(urlElement);
                }
                case "image" -> {
                    Element imageElement = new Image((String) jsonElement.get("body"));
                    noteElements.add(imageElement);
                }
            }
        }
        return noteElements;
    }

    private JSONObject readJSON(String filename) throws IOException, ParseException
    {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(reader);
    }

    public ArrayList<String> getSummary(ArrayList<Integer> iDs)
    {
        ArrayList<String> summaries = new ArrayList<>();
        for (Note note : notes)
        {
            int currentID = note.getId();
            if (iDs.contains(currentID)) {
                Element firstElement = note.getNoteElements().get(0);
                if (firstElement.getType().equals("text")) {
                    String body = firstElement.getBody();
                    int length = Math.min(body.length(), 40);
                    summaries.add(body.substring(0, length));
                } else {
                    summaries.add("Click to view content. Note only has images and/or URLs.");
                }
            }
        }
        return summaries;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getFullNote(ArrayList<Integer> iDs) throws FileNotFoundException {
        ArrayList<ArrayList<ArrayList<String>>> fullNoteElements = new ArrayList<>();
        ArrayList<ArrayList<String>> text = new ArrayList<>();
        ArrayList<ArrayList<String>> url = new ArrayList<>();
        ArrayList<ArrayList<String>> image = new ArrayList<>();

        for (Integer id : iDs) {
            Note noteById = getNoteById(id);
            ArrayList<ArrayList<String>> separatedElements = separateElements(noteById.getNoteElements());
            text.add(separatedElements.get(0));
            url.add(separatedElements.get(1));
            image.add(separatedElements.get(2));
        }
        fullNoteElements.add(text);
        fullNoteElements.add(url);
        fullNoteElements.add(image);
        return fullNoteElements;
    }

    public void sortedOrder(ArrayList<Integer> iDs, ArrayList<String> titles)
    {
        int n = iDs.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (titles.get(j).compareTo(titles.get(j + 1)) > 0) {
                    String tempString = titles.get(j);
                    titles.set(j, titles.get(j + 1));
                    titles.set(j + 1, tempString);

                    int tempInt = iDs.get(j);
                    iDs.set(j, iDs.get(j + 1));
                    iDs.set(j + 1, tempInt);
                }
            }
        }
    }

    public ArrayList<ArrayList<String>> separateElements(ArrayList<Element> noteByIdElements) throws FileNotFoundException {
        ArrayList<ArrayList<String>> separatedElements = new ArrayList<>();

        ArrayList<String> textToDisplay = new ArrayList<>();
        ArrayList<String> urlToDisplay = new ArrayList<>();
        ArrayList<String> imageToDisplay = new ArrayList<>();

        for (Element noteByIdElement : noteByIdElements)
        {
            ArrayList<String> lines = noteByIdElement.load();
            switch (noteByIdElement.getType()) {
                case "text" -> textToDisplay.addAll(lines);
                case "url" -> urlToDisplay.addAll(lines);
                case "image" -> imageToDisplay.addAll(lines);
            }
        }
        separatedElements.add(textToDisplay);
        separatedElements.add(urlToDisplay);
        separatedElements.add(imageToDisplay);
        return separatedElements;
    }
}
