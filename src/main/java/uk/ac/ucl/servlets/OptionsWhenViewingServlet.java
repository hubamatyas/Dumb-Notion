package uk.ac.ucl.servlets;

import org.json.simple.parser.ParseException;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;

@WebServlet("/changeView.html")
public class OptionsWhenViewingServlet extends HttpServlet
{

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        Model model = null;
        try {
            model = ModelFactory.getModel();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String option = request.getParameter("viewOptions");
        ArrayList<Integer> iDs = decodeParameter(request.getParameter("contentToView"));
        ArrayList<String> titles = model.getNoteTitles(iDs);

        request.setAttribute("noteIDs", iDs);
        request.setAttribute("noteTitles", titles);
        ServletContext context = getServletContext();

        switch (option) {
            case "summary" -> {
                ArrayList<String> summaries = model.getSummary(iDs);
                request.setAttribute("summary", summaries);
                RequestDispatcher dispatchSummary = context.getRequestDispatcher("/noteListWithSummary.jsp");
                dispatchSummary.forward(request, response);
            }
            case "full" -> {
                ArrayList<ArrayList<ArrayList<String>>> fullNoteElements = model.getFullNote(iDs);
                request.setAttribute("noteTexts", fullNoteElements.get(0));
                request.setAttribute("noteURLs", fullNoteElements.get(1));
                request.setAttribute("noteImagePaths", fullNoteElements.get(2));
                RequestDispatcher dispatchFull = context.getRequestDispatcher("/noteListWithFullNote.jsp");
                dispatchFull.forward(request, response);
            }
            case "added" -> {
                RequestDispatcher dispatchAdded = context.getRequestDispatcher("/noteList.jsp");
                dispatchAdded.forward(request, response);
            }
            case "sorted" -> {
                model.sortedOrder(iDs, titles);
                request.setAttribute("noteIDs", iDs);
                request.setAttribute("noteTitles", titles);
                RequestDispatcher dispatchSorted = context.getRequestDispatcher("/noteList.jsp");
                dispatchSorted.forward(request, response);
            }
        }
    }

    private ArrayList<Integer> decodeParameter(String base64String) throws IOException {
        byte[] objToBytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream bais = new ByteArrayInputStream(objToBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        ArrayList<Integer> decodedParameter = null;
        try {
            decodedParameter = (ArrayList<Integer>) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return decodedParameter;
    }
}
