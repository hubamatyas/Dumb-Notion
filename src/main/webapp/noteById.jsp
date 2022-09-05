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
        ArrayList<String> text = (ArrayList<String>) request.getAttribute("text");
        ArrayList<String> url = (ArrayList<String>) request.getAttribute("url");
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
            <a href="<%=line%>"><%=line%></a>
    <%
        }
    %>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
