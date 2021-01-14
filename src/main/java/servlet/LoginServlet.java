package servlet;

import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @PersistenceContext(unitName = "project")
    private EntityManager manager;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<User> result = manager.
                createQuery("select u from  User u where u.login=?1 and  u.password = ?2", User.class).
                setParameter(1, req.getParameter("login")).
                setParameter(2, Base64.getEncoder().encodeToString(req.getParameter("pass").getBytes())).
                getResultList();
        if (result.size() != 0) {
            session.setAttribute("session_id", result.get(0).getId());
            resp.sendRedirect("startpage");
        } else {
            session.setAttribute("login_info", "Увы, введены неправильные данные...");
            resp.sendRedirect("login-jsp");
        }
    }//close doPost

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login-jsp");
    }//close doPost
}//close class LoginServlet
