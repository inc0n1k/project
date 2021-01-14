package servlet;

import bean.CommentBean;
import bean.PublicationBean;
import bean.RatingBean;
import bean.UserBean;
import entity.Comment;
import entity.Publication;
import entity.Rating;
import entity.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/arac")
public class AddRatingsAndCommentsServlet extends HttpServlet {

    @EJB
    private UserBean userBean;

    @EJB
    private PublicationBean publicationBean;

    @EJB
    private RatingBean ratingBean;

    @EJB
    private CommentBean commentBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Comment comment = new Comment();
        User user = userBean.getUser(Long.parseLong(session.getAttribute("session_id").toString()));
        Publication publication = publicationBean.getPublication(Long.parseLong(req.getParameter("publication").trim()));
//        try {
        if (req.getParameter("rating") != null) {
            Rating rating = new Rating();
            rating.setRating(Integer.parseInt(req.getParameter("rating")));
            rating.setPublication(publication);
            rating.setUser(user);
            ratingBean.addRating(rating);
        }
        comment.setUser(user);
        comment.setPublication(publication);
        comment.setComment(req.getParameter("comment"));
        commentBean.addComment(comment);
        resp.sendRedirect("arac?publication=" + publication.getId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }//close doPost

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("session_id") == null) {
            resp.sendRedirect("login-jsp");
            return;
        }
        User user = userBean.getUser(Long.parseLong(session.getAttribute("session_id").toString()));
        req.setAttribute("user", user);
        long public_id = 0;
        if (req.getParameter("publication") == null) {
            /*if (session.getAttribute("publication") == null) {
                resp.sendRedirect("publications");
            } else {
            public_id = ((Publication) session.getAttribute("publication")).getId();
            setAttributesForSession(session, req, resp, ratingBean, commentBean, publicationBean, public_id);
            }*/
            resp.sendRedirect("publications");
        } else {
            if (req.getParameter("publication").matches("\\d+")) {
                public_id = Long.parseLong(req.getParameter("publication").trim());
                setAttributesForSession(session, req, resp, ratingBean, commentBean, publicationBean, public_id);
            } else {
                resp.sendRedirect("publications");
            }
        }
        /*if (session.getAttribute("publication") == null) {
            if (req.getParameter("publication") == null) {
                resp.sendRedirect("publications");
            } else {

            }
        } else {
            req.getRequestDispatcher("arac-jsp").forward(req, resp);
        }*/
    }//close doGet

    private static void setAttributesForSession(HttpSession session, HttpServletRequest req,
                                                HttpServletResponse resp, RatingBean ratingBean, CommentBean commentBean,
                                                PublicationBean publicationBean, long public_id) throws ServletException, IOException {
        Publication publication = publicationBean.getPublication(public_id);
        if (publication == null) {
            resp.sendRedirect("publications");
            return;
        }
        session.setAttribute("publication", publication);
        session.setAttribute("ratings", ratingBean.getRatingsForPublication(publication.getId()));
        session.setAttribute("comments", commentBean.getCommentsForPublication(publication.getId()));
        req.setAttribute("avg_rating", ratingBean.getAvgRatingForPublication(publication.getId()));
        req.getRequestDispatcher("arac-jsp").forward(req, resp);
    }
}//close class AddRatingsAndCommentsServlet