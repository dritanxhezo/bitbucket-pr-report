package net.ngeor.bprr;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ngeor.bitbucket.Repository;

/**
 * Main servlet.
 */
@WebServlet("/index")
public class HomeServlet extends HttpServlet {
    private Factory factory = Factory.INSTANCE;

    void setFactory(Factory factory) {
        this.factory = factory;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        boolean isLoggedIn  = session.getAttribute("owner") != null;
        String path         = isLoggedIn ? "index" : "login";

        if (isLoggedIn) {
            BitbucketClient bitbucketClient = factory.bitbucketClient(req);
            List<Repository> repositories   = bitbucketClient.getAllRepositories();
            req.setAttribute("repositories", repositories);
        }

        RequestDispatcher requestDispatcher =
            req.getServletContext().getRequestDispatcher(String.format("/WEB-INF/jsp/%s.jsp", path));
        requestDispatcher.forward(req, resp);
    }
}
