package servlet;

import bean.PublicationBean;
import bean.UserBean;
import entity.Category;
import entity.Publication;
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
import java.io.IOException;
import java.util.List;

@WebServlet("/createpost")
public class CreatePostServlet extends HttpServlet {

    @PersistenceContext(unitName = "project")
    EntityManager manager;

    @EJB
    PublicationBean publicationBean;

    @EJB
    UserBean userBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Publication publication = new Publication();
        try {
            publication.setCategory(manager.find(Category.class, Long.parseLong(req.getParameter("category").trim())));
            publication.setUser(manager.find(User.class, Long.parseLong(session.getAttribute("session_id").toString().trim())));
            publication.setState(false);
            publication.setTitle(req.getParameter("new_post_name").trim());
            publication.setContent(req.getParameter("new_post_text").trim());
//            manager.persist(publication);
            publicationBean.addPublication(publication);
            resp.sendRedirect("start-page-jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//close doPost

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("session_id") == null) {
            resp.sendRedirect("login-jsp");
        } else {
            User user = userBean.getUser(Long.parseLong(session.getAttribute("session_id").toString()));
            if (!user.getBlocked()) {
                req.setAttribute("user", user);
                List<Category> categories = manager.
                        createQuery("select c from Category c where c.visible = true ", Category.class).
                        getResultList();
                req.setAttribute("categories", categories);
                req.getRequestDispatcher("create-post-jsp").forward(req, resp);
            } else {
                resp.sendRedirect("start-page-jsp");
            }
        }
    }//close doPost

}//close class CreatePostServlet
