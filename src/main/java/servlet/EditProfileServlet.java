package servlet;

import bean.RoleBean;
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
import java.util.Base64;

@WebServlet("/editprofile")
public class EditProfileServlet extends HttpServlet {

    @EJB
    private UserBean userBean;

    @EJB
    private RoleBean roleBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        User edit_user = userBean.getUser(Long.parseLong(req.getParameter("edit_user")));
        if (user.getPassword().equals(Base64.getEncoder().encodeToString(req.getParameter("pass").getBytes()))) {
            edit_user.setName(req.getParameter("name"));
            edit_user.setSurname(req.getParameter("surname"));
            edit_user.setRole(roleBean.getRole(Long.parseLong(req.getParameter("role"))));
            edit_user.setBlocked(Boolean.parseBoolean(req.getParameter("blocked")));
            userBean.updateUser(edit_user);
            session.setAttribute("user", user);
            resp.sendRedirect("editprofile?edit_user=" + edit_user.getId());
        } else {
            req.setAttribute("error", "Неправильный пароль...");
            resp.sendRedirect("editprofile?edit_user=" + edit_user.getId());
//            req.getRequestDispatcher("editprofile?edit_user=" + edit_user.getId()).forward(req, resp);
        }
    }//close doPost

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
        req.setAttribute("user", user);
        User edit_user = userBean.getUser(Long.parseLong(req.getParameter("edit_user")));
        req.setAttribute("edit_user", edit_user);
        req.setAttribute("roles", roleBean.getAllRoles());
        req.getRequestDispatcher("editProfileJsp.jsp").forward(req, resp);
    }//close doGet
}//close class Edit