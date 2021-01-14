package servlet;

import bean.PublicationBean;
import bean.RatingBean;
import entity.Publication;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/top10public")
public class Top10Publications extends HttpServlet {

    @EJB
    private PublicationBean publicationBean;

    @EJB
    private RatingBean ratingBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("session_id") == null) {
            resp.sendRedirect("login-jsp");
            return;
        }
        PrintWriter pw = resp.getWriter();
        List<Publication> publications = publicationBean.getTop10Publications();
        for (Publication publication : publications) {
            Double avg_rating = ratingBean.getAvgRatingForPublication(publication.getId()) == null ? 0.0 : ratingBean.getAvgRatingForPublication(publication.getId());
            pw.print("Post name: ");
            pw.println(publication.getTitle());
            pw.print("Average rating: ");
            pw.println(String.format("%.2f", avg_rating));
            pw.println("----");
        }
        pw.close();
    }
}
