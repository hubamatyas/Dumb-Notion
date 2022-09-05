<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.ObjectOutputStream" %>
<%@ page import="java.util.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Titles</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <%
        ArrayList<String> titles = (ArrayList<String>) request.getAttribute("noteTitles");
        ArrayList<Integer> iDs = (ArrayList<Integer>) request.getAttribute("noteIDs");

    %>

    <h2>Notes</h2>

    <%
        if (titles.isEmpty())
        {
    %>
            <h3>Nothing to display</h3>
    <%
        }
        else
        {
            for (int i = 0; i < titles.size(); i++)
            {
                int id = iDs.get(i);
                String title = titles.get(i);
                String href = "editNote.html?id=" + id;
    %>
                <h3 style="display: inline"><a href="<%=href%>"><%=title%></a></h3>
                <form method="POST" action="/deleteNote.html" style="display: inline">
                    <input type="hidden" name="id" value="<%=id%>" />
                    <input type="submit" value="Delete" style="color: red">
                </form>
                <br>
                <br>
    <%
            }
        }
    %>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
