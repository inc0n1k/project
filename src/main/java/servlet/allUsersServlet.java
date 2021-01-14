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
import java.util.List;

@WebServlet("/allusers")
public class allUsersServlet extends HttpServlet {

    @EJB
    private UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("session_id") == null) {
            resp.sendRedirect("login-jsp");
            return;
        }
        User user = userBean.getUser(Long.parseLong(session.getAttribute("session_id").toString()));
        if (user.getRole().getRole().equals("Пользователь")) {
            resp.sendRedirect("start-page-jsp");
            return;
        }
        List<User> users = userBean.getAllUsers();
        req.setAttribute("user", user);
        req.setAttribute("users", users);
        req.getRequestDispatcher("allProfilesJsp.jsp").forward(req, resp);
    }
}
