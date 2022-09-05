package uk.ac.ucl.servlets;

import org.json.simple.parser.ParseException;
import uk.ac.ucl.model.*;
import uk.ac.ucl.model.Image;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/addAndEditNote.html")
public class AddAndEditNoteServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = null;
        try {
            model = ModelFactory.getModel();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean emptyNote = true;
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        String url = request.getParameter("url");
        String image = request.getParameter("image");
        String mode = request.getParameter("mode");
        ArrayList<Element> noteElements = new ArrayList<>();

        if (!text.isEmpty())
        {
            Element textElement = new Text(text);
            noteElements.add(textElement);
            emptyNote = false;
        }
        if (!url.isEmpty())
        {
            Element urlElement = new Url(url);
            noteElements.add(urlElement);
            emptyNote = false;
        }
        if (!image.isEmpty())
        {
            Element imageElement = new Image(image);
            noteElements.add(imageElement);
            emptyNote = false;
        }

        ServletContext context = getServletContext();
        if (emptyNote)
        {
            request.setAttribute("result", "");
            request.setAttribute("message", "Can't add empty note.");
            RequestDispatcher dispatchEmpty = context.getRequestDispatcher("/addResponse.jsp");
            dispatchEmpty.forward(request, response);
        }
        else if (mode.equals("add"))
        {
            model.addNote(title, noteElements);
            request.setAttribute("result", title);
            request.setAttribute("message", "added successfully");
            RequestDispatcher dispatchNotEmptyAdd = context.getRequestDispatcher("/addResponse.jsp");
            dispatchNotEmptyAdd.forward(request, response);
        }
        else if (mode.equals("edit"))
        {
            int id = Integer.parseInt(request.getParameter("id"));
            model.saveNote(id, title, noteElements);
            request.setAttribute("result", title);
            request.setAttribute("message", "edited and saved successfully");
            RequestDispatcher dispatchNotEmptyEdit = context.getRequestDispatcher("/addResponse.jsp");
            dispatchNotEmptyEdit.forward(request, response);
        }

    }
}
