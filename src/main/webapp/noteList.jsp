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

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(iDs);
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
                String href = "notes.html?id=" + id;
    %>
                <h3><a href="<%=href%>"><%=title%></a></h3>
    <%
            }
        }
    %>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
