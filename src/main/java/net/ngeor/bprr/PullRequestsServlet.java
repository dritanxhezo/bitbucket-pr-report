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
        String id = req.getParameter("id");
        PullRequestsResponse pullRequestsResponse;
        if (id != null) {
            PullRequestResponse pullRequestResponse = getSinglePullRequest(req.getParameter("repo"), accessToken, id);
            pullRequestsResponse = new PullRequestsResponse();
            pullRequestsResponse.setValues(new PullRequestResponse[] { pullRequestResponse });
        } else {
            pullRequestsResponse = getPullRequests(
                    req.getParameter("repo"),
                    accessToken);
        }
        req.setAttribute("pullRequests", pullRequestsResponse);
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
        String[] parts = fullRepoName.split("\\/");
        pullRequestsRequest.setOwner(parts[0]);
        pullRequestsRequest.setRepositorySlug(parts[1]);
        pullRequestsRequest.setAccessToken(accessToken);

        return pullRequestsRequest.execute();
    }

    private PullRequestResponse getSinglePullRequest(String fullRepoName, String accessToken, String id) throws IOException {
        if (accessToken == null) {
            throw new IllegalStateException("No accessToken!");
        }

        if (fullRepoName == null) {
            throw new IllegalArgumentException("fullRepoName cannot be null");
        }

        PullRequestRequest pullRequestRequest = new PullRequestRequest();
        String[] parts = fullRepoName.split("\\/");
        pullRequestRequest.setOwner(parts[0]);
        pullRequestRequest.setRepositorySlug(parts[1]);
        pullRequestRequest.setAccessToken(accessToken);
        pullRequestRequest.setId(id);

        return pullRequestRequest.execute();
    }

}