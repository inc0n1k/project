package servlet;

import bean.UserBean;
import entity.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/startpage")
public class startPageServlet extends HttpServlet {

    @EJB
    private UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("session_id") == null) {
            resp.sendRedirect("login-jsp");
        } else {
            User user = userBean.getUser(Long.parseLong(session.getAttribute("session_id").toString()));
            req.setAttribute("user", user);
            req.getRequestDispatcher("start-page-jsp").forward(req, resp);
        }
    }
}
