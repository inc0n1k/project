<%@ page import="java.util.List" %>
<%@ page import="entity.User" %>
<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Profiles</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("login-jsp");
    }
//    else {
    User user = (User) request.getAttribute("user");
    List<User> users = (List<User>) request.getAttribute("users");
    switch (user.getRole().getRole()) {
        case "Администратор":
            for (User for_user : users) {
%>
<p>
    <a href="editprofile?edit_user=<%=for_user.getId()%>">
        <%= for_user.getSurname() %>
        <%= for_user.getName() %>
        (<%= for_user.getLogin() %>)
    </a>
</p>
<%
        }
        break;
    case "Модератор":
        for (User for_user : users) {
            if (!for_user.getRole().getRole().equals("Администратор")) {
%>
<p>
    <a href="editprofile?edit_user=<%=for_user.getId()%>">
        <%= for_user.getSurname() %>
        <%= for_user.getName() %>
        (<%= for_user.getLogin() %>)
    </a>
</p>
<%
                }
            }
            break;
        case "Пользователь":
            response.sendRedirect("start-page-jsp");
            break;
    }
//    }
%>
<p>
    <button class="button" onclick="window.location.href = 'start-page-jsp'">Back</button>
</p>
</body>
</html>
