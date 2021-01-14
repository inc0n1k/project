<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
</head>
<body>
<link href="css/login.css" rel="stylesheet">
<div class="content_box">
    <form class="login_form" action="login" method="post" autocomplete="off">
        <%
            if (session.getAttribute("session_id") != null)
                response.sendRedirect("startpage");
        %>
        <% if (session.getAttribute("login_info") != null) {%>
        <div class="error">
            <%= session.getAttribute("login_info")%>
        </div>
        <% session.removeAttribute("login_info"); %>
        <%
            }
        %>
        <input class="input" required placeholder="Input login..." type="text" name="login">
        <input class="input" required placeholder="Input password..." type="password" name="pass">
        <div class="button_form">
            <div>
                <input class="button" type="submit" value="Login"></div>
            <div>
                <button class="button" onclick="window.location.href = 'registration-jsp'">Registration</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>