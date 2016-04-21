package net.ngeor.bprr;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PullRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accessToken = (String)req.getSession().getAttribute("accessToken");
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/pull-requests.jsp");
        req.setAttribute("pullRequests", getPullRequests(req.getParameter("repo"), accessToken));
        requestDispatcher.forward(req, resp);
    }

    private PullRequestsResponse getPullRequests(String fullRepoName, String accessToken) throws IOException {
        if (accessToken == null) {
            throw new IllegalStateException("No accessToken!");
        }

        if (fullRepoName == null) {
            throw new IllegalArgumentException("fullRepoName cannot be null");
        }

        PullRequestsRequest pullRequestsRequest = new PullRequestsRequest();
        return pullRequestsRequest.execute(
                fullRepoName,
                accessToken
        );
    }
}