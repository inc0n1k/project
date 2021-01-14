<%@ page import="entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Profile</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("/login-jsp");
        return;
    }
    User user = (User) request.getAttribute("user");
    if (user.getRole().getRole().equals("Пользователь")) {
        response.sendRedirect("start-page-jsp");
        return;
    }
    User edit_user = (User) request.getAttribute("edit_user");
%>
<form action="editprofile" method="post" autocomplete="off">
    <input hidden name="edit_user" value="<%= edit_user.getId() %>">
    <p>
        User name: (<%=edit_user.getLogin()%>)<br>
        <input value=<%=edit_user.getName()%> type="text" name="name">
    </p>
    <p>
        User surname:<br>
        <input value="<%= edit_user.getSurname()%>" type="text" name="surname">
    </p>
    <%
        List<Role> roles = (List<Role>) request.getAttribute("roles");
    %>
    <p>
        User role:
        <select name="role">
            <%
                for (Role role : roles) {
            %>
            <option
                    <%
                        if (role.getRole().equals(edit_user.getRole().getRole())) {
                    %>
                    selected
                    <%
                        }
                    %>
                    value="<%= role.getId() %>">
                <%= role.getRole() %>
            </option>
            <%}%>
        </select>
    </p>
    <p>Блокировка:
        <input type="radio" name="blocked" value="true" <%= edit_user.getBlocked()?"checked":"" %>>Yes
        <input type="radio" name="blocked" value="false" <%= edit_user.getBlocked()?"":"checked" %>>No
    </p>
    <p>
        Введите пароль для сохранения изменений:
    </p>
    <p>
        <%
            if (request.getAttribute("error") != null) {
                out.print(request.getAttribute("error"));
            }
        %>
    </p>
    <input placeholder="Password..." type="password" name="pass">
    <input class="button" type="submit" value="Save">
</form>
<p>
    <button class="button" onclick="window.location.href = 'allusers'">Back</button>
</p>

</body>
</html>