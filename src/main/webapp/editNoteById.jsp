<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.util.ArrayList" %>
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
        String title = (String) request.getAttribute("title");
        String text = (String) request.getAttribute("text");
        String url = (String) request.getAttribute("url");
        String image = (String) request.getAttribute("image");
        int id = (int) request.getAttribute("id");
    %>
    <h1>Edit Note</h1>
    <form method="POST" action="/addAndEditNote.html">
        <input type="hidden" name="mode" value="edit" />
        <input type="hidden" name="id" value="<%=id%>" />

        <label for="title-box">Title</label>
        <input type="text" name="title" id="title-box" value="<%=title%>" placeholder="Enter note title"/>
        <label for="text-field">Text</label>
        <textarea name="text" rows="20" id="text-field" cols="80" placeholder="Write your notes here"><%=text%></textarea>
        <label for="url-link">URL</label>
        <input type="text" name="url" id="url-link" value="<%=url%>" placeholder="Enter URL"/>
        <label for="image-file">Image</label>
        <input type="file" name="image" id="image-file" accept="image/*" onchange="loadFile(event)">
        <img id="output" width="500" />
        <br>
        <input type="submit" value="Save Note">
    </form>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
