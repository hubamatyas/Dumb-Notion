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
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/notes.html")
public class NoteByIdServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = null;
        try {
            model = ModelFactory.getModel();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int id = Integer.parseInt(request.getParameter("id"));
        Note noteById = model.getNoteById(id);
        String title = noteById.getTitle();

        ArrayList<ArrayList<String>> separatedElements = model.separateElements(noteById.getNoteElements());

        request.setAttribute("title", title);
        request.setAttribute("text", separatedElements.get(0));
        request.setAttribute("url", separatedElements.get(1));
        request.setAttribute("imagePath", separatedElements.get(2));

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/noteById.jsp");
        dispatch.forward(request, response);
    }
}
