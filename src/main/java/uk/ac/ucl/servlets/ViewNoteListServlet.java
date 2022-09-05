package uk.ac.ucl.servlets;

import org.json.simple.parser.ParseException;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/noteList.html")
public class ViewNoteListServlet extends HttpServlet
{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        Model model = null;
        try {
            model = ModelFactory.getModel();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> iDs = model.getNoteIDs();
        ArrayList<String> titles = model.getNoteTitles(iDs);
        request.setAttribute("noteTitles", titles);
        request.setAttribute("noteIDs", iDs);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/noteList.jsp");
        dispatch.forward(request, response);
    }
}
