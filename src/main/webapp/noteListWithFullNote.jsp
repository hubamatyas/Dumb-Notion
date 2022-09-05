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
  <title>Dumb Notion</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <%
        ArrayList<Integer> noteIDs = (ArrayList<Integer>) request.getAttribute("noteIDs");
        ArrayList<String> noteTitles = (ArrayList<String>) request.getAttribute("noteTitles");
        ArrayList<ArrayList<String>> noteTexts = (ArrayList<ArrayList<String>>) request.getAttribute("noteTexts");
        ArrayList<ArrayList<String>> noteURLs = (ArrayList<ArrayList<String>>) request.getAttribute("noteURLs");
        ArrayList<ArrayList<String>> noteImagePaths = (ArrayList<ArrayList<String>>) request.getAttribute("noteImagePaths");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(noteIDs);
        oos.flush();
        String encodedIDs = new String(Base64.getEncoder().encode(baos.toByteArray()));
    %>

        <h2>Notes</h2>
        <form method="POST" action="/changeView.html">
            <input type="hidden" name="contentToView" value="<%=encodedIDs%>" />
            <label for="viewOptions">View</label>
            <select name="viewOptions" id="viewOptions" onchange="this.form.submit()">
                <option></option>
                <option value="added">Order added</option>
                <option value="sorted">Sorted order</option>
                <option value="summary">Summary</option>
                <option value="full">Full notes</option>
            </select>
        </form>
    <%
        for (int i = 0; i < noteIDs.size(); i++)
        {
            String title = noteTitles.get(i);
            ArrayList<String> text = noteTexts.get(i);
            ArrayList<String> url = noteURLs.get(i);
    %>
            <h3><%=title%></h3>

    <%
            for (String line : text)
            {
    %>
                <p><%=line%></p>
    <%
            }
    %>

    <%
            for (String line : url)
            {
    %>
                <a href="<%=line%>" style="display: block"><%=line%></a>
    <%
            }
    %>
            <br>
    <%
        }
    %>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
