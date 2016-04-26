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
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException ex) {
            id = 0;
        }

        PullRequestsResponse pullRequestsResponse;
        if (id > 0) {
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

        String[] parts = fullRepoName.split("\\/");
        PullRequestsRequest pullRequestsRequest = new PullRequestsRequest(parts[0], parts[1]);

        DefaultBitbucketClient bitbucketClient = new DefaultBitbucketClient();
        bitbucketClient.setAccessToken(accessToken);
        return bitbucketClient.execute(pullRequestsRequest, PullRequestsResponse.class);
    }

    private PullRequestResponse getSinglePullRequest(String fullRepoName, String accessToken, int id) throws IOException {
        if (accessToken == null) {
            throw new IllegalStateException("No accessToken!");
        }

        if (fullRepoName == null) {
            throw new IllegalArgumentException("fullRepoName cannot be null");
        }

        String[] parts = fullRepoName.split("\\/");
        PullRequestRequest pullRequestRequest = new PullRequestRequest(parts[0], parts[1], id);

        DefaultBitbucketClient bitbucketClient = new DefaultBitbucketClient();
        bitbucketClient.setAccessToken(accessToken);
        return bitbucketClient.execute(pullRequestRequest, PullRequestResponse.class);
    }
}