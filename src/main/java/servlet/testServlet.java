package servlet;

import bean.CommentBean;
import com.google.gson.Gson;
import entity.TestClass;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
public class testServlet extends HttpServlet {

    @EJB
    private CommentBean commentBean;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("test.jsp").forward(req, resp);
    }//close doGet

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");
        TestClass testClass = new TestClass();
        testClass.setId(1);
        testClass.setName("Name");
        testClass.setComments("dljkfghdfghdkfjghd");

        Gson gson = new Gson();
        String str = gson.toJson(testClass);
        resp.getWriter().write(str);
    }//close doPost
}