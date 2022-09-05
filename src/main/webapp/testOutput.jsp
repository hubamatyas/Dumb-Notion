<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h1>Test Output</h1>
  <%
    List<String> words = (List<String>) request.getAttribute("result");
    if (words.size() !=0)
    {
    %>
    <ul>
      <%
        for (String word : words)
        {
      %>
      <li><%=word%></li>
     <% }
    } else
    {%>
      <p>Nothing found</p>
  <%}%>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>