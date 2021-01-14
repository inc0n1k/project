<%@ page import="java.util.List" %>
<%@ page import="entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Ratings And Comments</title>
</head>
<body>
<%
    if (session.getAttribute("session_id") == null) {
        response.sendRedirect("/login-jsp");
        return;
    }
    if ((request.getAttribute("user") == null) || (session.getAttribute("publication") == null)) {
        response.sendRedirect("publications");
        return;
    }
    User user = (User) request.getAttribute("user");
    Publication publication = (Publication) session.getAttribute("publication");
    session.removeAttribute("publication");
    Double rating = request.getAttribute("avg_rating") == null ? 0.0 : (Double) request.getAttribute("avg_rating");
%>
<p>
    <b>Publication name:</b> <%=publication.getTitle()%>
</p>
<p>
    <b>Publication text:</b> <%=publication.getContent()%>
</p>
<p>
    <b>Publication category:</b> <%=publication.getCategory().getCategory()%>
</p>
<p>
    <b>Average rating: </b><%= String.format("%.2f", rating) %>
</p>
<% if (!user.getBlocked()) {%>
<form action="arac" method="post" autocomplete="off">
    <input hidden name="publication" value="<%= publication.getId()%>">
    <%
        List<Rating> ratings = (List<Rating>) session.getAttribute("ratings");
        session.removeAttribute("ratings");
        boolean print_rating = true;
        for (Rating chek_rating : ratings) {
            if (chek_rating.getUser().getId().equals(user.getId())) {
                print_rating = false;
                break;
            }
        }
        if (print_rating) {
    %>
    <p><b>Rating:</b></p>
    <p>
        <input type="radio" name="rating" value="1" checked>1
        <input type="radio" name="rating" value="2">2
        <input type="radio" name="rating" value="3">3
        <input type="radio" name="rating" value="4">4
        <input type="radio" name="rating" value="5">5
    </p>
    <%}%>
    <p>
        <textarea required name="comment" placeholder="Input post comment..."></textarea>
    </p>
    <input class="button" type="submit" value="Отправить">
</form>
<%}%>
<p>
    <button class="button" onclick="window.location.href = 'publications?for=edit'">Back</button>
</p>
<%
    List<Comment> comments = (List<Comment>) session.getAttribute("comments");
    session.removeAttribute("comments");
    if (comments.size() > 0) {
        if (session.getAttribute("error_remove") != null) {
            out.println(session.getAttribute("error_remove"));
            session.removeAttribute("error_remove");
        }
%>
<form action="rrac" method="post" autocomplete="off">
    <%
        if (!user.getBlocked()) {
            if (!user.getRole().getRole().equals("Пользователь")) {
    %>
    <input hidden name="publication" value="<%= request.getParameter("publication")%>">
    <input class="button" type="submit" value="Remove">
    <%
            }
        }
    %>
    <p>************************************<br>
        <% for (Comment comment : comments) {%>
        <% if (!user.getBlocked()) {
            if (!user.getRole().getRole().equals("Пользователь")) {%>
        <input type="checkbox" name="remove" value="<%= comment.getId() %>">
        <%
                }
            }
        %>
        <b>Comment: </b><%=comment.getComment()%>
    </p>
    <p>
        <b>User name: </b><%=comment.getUser().getName()%> (<%= comment.getUser().getLogin()%>)
    </p>
    <p>
        <br>************************************
    </p>
    <%
        }//close for
    %>
</form>
<%
    }//close if publication.getComments().size()
%>
</body>
</html>
