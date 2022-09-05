package uk.ac.ucl.model;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class ModelFactory
{
  private static Model model;

  public static Model getModel() throws IOException, ParseException {
    if (model == null)
    {
      model = new Model();
      model.loadNotes();
    }
    return model;
  }
}
