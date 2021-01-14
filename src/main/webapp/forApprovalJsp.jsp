<%@ page import="java.util.List" %>
<%@ page import="entity.Publication" %>
<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>For Approval</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("/login-jsp");
        return;
    }
    User user = (User) request.getAttribute("user");
    if (user.getRole().getRole().equals("Пользоатель")) {
        response.sendRedirect("start-page-jsp");
        return;
    }
    if (request.getAttribute("publications") == null) {
        response.sendRedirect("publications?for=approve");
        return;
    }
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
        for (Publication publication : publications) {
    %>
    <p>
        <a href="approve?publication=<%=publication.getId()%>" name="publication">
            <%= publication.getTitle()%>
        </a>
    </p>
    <%}%>
<p>
    <button class="button" onclick="window.location.href = 'start-page-jsp'">Back</button>
</p>
</body>
</html>
