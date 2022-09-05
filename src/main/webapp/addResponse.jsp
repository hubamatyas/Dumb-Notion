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
  String title = (String) request.getAttribute("result");
  String message = (String) request.getAttribute("message");
  %>
  <h1><%=title%> <%=message%></h1>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>