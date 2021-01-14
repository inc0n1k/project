<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Page</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") != null) {
        response.sendRedirect("startpage");
    }
%>
<% if (session.getAttribute("reg_error") != null) { %>
<p>
    <%= session.getAttribute("reg_error")%>
</p>
<%
    }
    session.removeAttribute("reg_error");
%>
<form action="registration" method="post" autocomplete="off">
    <p>
        <input class="input" required placeholder="Input login..." type="text" name="login">
    </p>
    <p>
        <input class="input" required placeholder="Input password..." type="password" name="pass">
    </p>
    <p>
        <input class="input" required placeholder="Input name..." type="text" name="name">
    </p>
    <p>
        <input class="input" required placeholder="Input surname..." type="text" name="surname">
    </p>
    <p>
        <input class="button" type="submit" value="Registration">
        <button class="button" onclick="window.location.href = 'login-jsp'">Back</button>
    </p>
</form>
</body>
</html>
