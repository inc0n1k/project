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
import javax.transaction.Transactional;
import java.io.IOException;

@WebServlet("/approve")
public class approveServlet extends HttpServlet {

    @EJB
    private PublicationBean publicationBean;

    @EJB
    private UserBean userBean;

    @Override
    @Transactional
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Publication publication = publicationBean.getPublication(Long.parseLong(req.getParameter("publication")));
        boolean approve = Boolean.parseBoolean(req.getParameter("approve"));
        if (approve) {
            publication.setState(true);
            publicationBean.updatePublication(publication);
        } else {
            publicationBean.removePublication(publication);
        }
        resp.sendRedirect("publications?for=approve");
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
        Publication publication = publicationBean.getPublication(Long.parseLong(req.getParameter("publication")));
        req.setAttribute("publication", publication);
        req.setAttribute("user", user);
        req.getRequestDispatcher("approvalJsp.jsp").forward(req, resp);
    }
}//close class approveServlet