<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Start Page</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("login-jsp");
    } else {
        User user = (User) request.getAttribute("user");
        if (user == null) {
            response.sendRedirect("startpage");
            return;
        }
%>
<p>
    <a href="userprofile">User Profile</a>
</p>
<%
    if (!user.getBlocked()) {
%>
<p>
    <a href="createpost">Create Post</a>
</p>
<%}%>
<p>
    <a href="publications?for=edit">All Publications</a>
</p>
<p>
    <a href="top10public">Top 10</a>
</p>
<%
    if (user.getRole().getId() != 3L) {
        if (!user.getBlocked()) {
%>
<p>
    <a href="allusers">All profiles</a>
</p>
<p>
    <a href="publications?for=approve">For approval</a>
</p>
<%
            }
        }
    }
%>
<p>
    <button class="button" onclick="window.location.href = 'exit'">Exit</button>
</p>
</body>
</html>
