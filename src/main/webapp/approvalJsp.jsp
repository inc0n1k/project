<%@ page import="entity.Publication" %>
<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Approval Jsp</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("login-jsp");
        return;
    }
    User user = (User) request.getAttribute("user");
    if (user.getRole().getRole().equals("Пользователь")) {
        response.sendRedirect("start-page-jsp");
        return;
    }
    Publication publication = (Publication) request.getAttribute("publication");
%>
<p>
    Public title: <%=publication.getTitle()%>
</p>
<p>
    Public category: <%= publication.getCategory().getCategory()%>
</p>
<p>
    Public user name: <%= publication.getUser().getName()%>(<%=publication.getUser().getLogin()%>)
</p>
<p>
    Public content: <%= publication.getContent()%>
</p>
<p>
    Public date: <%= publication.getPublic_date() %>
</p>
<form action="approve" method="post" autocomplete="off">
    <input hidden name="publication" value="<%=publication.getId()%>">
    <input type="radio" name="approve" value="true">Approve
    <input type="radio" name="approve" value="false" checked>Refuse
    <p>
        <input class="button" type="submit">
    </p>
</form>
<p>
    <button class="button" onclick="window.location.href = 'publications?for=approve'">Back</button>
</p>
</body>
</html>
