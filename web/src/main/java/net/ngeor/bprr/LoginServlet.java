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
import net.ngeor.http.HttpClientImpl;
import net.ngeor.http.JsonHttpClient;
import net.ngeor.http.JsonHttpClientImpl;

/**
 * Main servlet.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String owner    = req.getParameter("owner");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        session.setAttribute("owner", owner);
        session.setAttribute("username", username);
        session.setAttribute("password", password);

        JsonHttpClient jsonHttpClient = new JsonHttpClientImpl(new HttpClientImpl(username, password));

        BitbucketClient bitbucketClient = new BitbucketClient(jsonHttpClient, owner);

        List<Repository> repositories = bitbucketClient.getAllRepositories();

        req.setAttribute("repositories", repositories);

        RequestDispatcher requestDispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
