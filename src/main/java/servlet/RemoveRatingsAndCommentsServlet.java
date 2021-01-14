package servlet;

import bean.CommentBean;
import entity.Comment;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/rrac")
public class RemoveRatingsAndCommentsServlet extends HttpServlet {

    @EJB
    private CommentBean commentBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("session_id") == null) {
            resp.sendRedirect("login-jsp");
            return;
        }
        if (req.getParameterValues("remove") == null) {
            resp.sendRedirect("arac?publication=" + req.getParameter("publication"));
            session.setAttribute("error_remove", "No comments were selected...");
            return;
        }

//        User user = manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString()));
//        if (!user.getRole().getRole().equals("Пользователь")) {
        String[] for_remove = req.getParameterValues("remove");
        Comment comment;
        for (String remove_com : for_remove) {
            comment = commentBean.getComment(Long.parseLong(remove_com));
            commentBean.removeComment(comment);
                    /*manager.createQuery("select c from Comment  c where c.id  = ?1", Comment.class).
                    setParameter(1, Long.parseLong(remove_com)).
                    getSingleResult();
            manager.remove(comment);*/
        }
        resp.sendRedirect("arac?publication=" + req.getParameter("publication"));
//        }
    }//close doPost
}//close class RemoveRatingsAndCommentsServlet
