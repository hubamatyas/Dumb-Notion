package uk.ac.ucl.servlets;

import org.json.simple.parser.ParseException;
import uk.ac.ucl.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteNote.html")
public class DeleteNoteServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = null;
        try {
            model = ModelFactory.getModel();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int id = Integer.parseInt(request.getParameter("id"));
        String title = String.valueOf(model.getNoteTitles(id));
        model.archiveNote(id);

        request.setAttribute("result", title);
        request.setAttribute("message", "deleted");

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/addResponse.jsp");
        dispatch.forward(request, response);

    }
}
