package servlet;

import bean.UserBean;
import entity.Role;
import entity.User;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @PersistenceContext(unitName = "project")
    EntityManager manager;

    @EJB
    UserBean userBean;

    @Override
    @Transactional(rollbackOn = Exception.class)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        List<User> users = manager.createQuery("select u from User u where u.login = ?1", User.class).
                setParameter(1, login).
                getResultList();
        HttpSession session = req.getSession();
        if (users.size() == 0) {
            if (login.matches("[A-Za-z][A-Za-z_\\d]{4,}")) {
                User new_user = new User();
                Role role = manager.find(Role.class, 3L);
                try {
                    new_user.setLogin(login);
                    new_user.setPassword(Base64.getEncoder().encodeToString(req.getParameter("pass").getBytes()));
                    new_user.setName(req.getParameter("name"));
                    new_user.setSurname(req.getParameter("surname"));
                    new_user.setRole(role);
                    new_user.setBlocked(false);
                    userBean.addUser(new_user);
                    resp.sendRedirect("login-jsp");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                session.setAttribute("reg_error", "Длина логина меньше 5 символов либо логин начинается со знака _ или с цифры");
                resp.sendRedirect("registration-jsp");
            }
        } else {
            session.setAttribute("reg_error", "Login exists...");
            resp.sendRedirect("registration-jsp");
        }
    } //close doPost

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("registration-jsp");
    }
} //close class RegistrationServlet