package servlet;

import bean.PublicationBean;
import bean.UserBean;
import entity.Publication;
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

@WebServlet("/publications")
public class PublicationsServlet extends HttpServlet {

    @EJB
    private PublicationBean publicationBean;

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
        List<Publication> publications;
        switch (req.getParameter("for")) {
            case "edit":
                publications = publicationBean.getAllPublications(true);
                req.setAttribute("publications", publications);
                req.setAttribute("user", user);
                req.getRequestDispatcher("publications-jsp").forward(req, resp);
                break;
            case "approve":
                if (user.getRole().getRole().equals("Пользователь")) {
                    resp.sendRedirect("publications?for=approve");
                    return;
                }
                publications = publicationBean.getAllPublications(false);
                req.setAttribute("publications", publications);
                req.setAttribute("user", user);
                req.getRequestDispatcher("forApprovalJsp.jsp").forward(req, resp);
                break;
            default:
                resp.sendRedirect("login-jsp");
        }
    }//close doGet
}//close class PublicationsServlet
