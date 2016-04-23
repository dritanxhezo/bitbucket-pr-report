package net.ngeor.bprr;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DemoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accessToken = (String)req.getSession().getAttribute("accessToken");
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/demo.jsp");
        req.setAttribute("repositories", getRepositories(accessToken));
        requestDispatcher.forward(req, resp);
    }

    private RepositoriesResponse getRepositories(String accessToken) throws IOException {
        if (accessToken == null) {
            throw new IllegalStateException("No accessToken!");
        }

        RepositoriesRequest repositoriesRequest = new RepositoriesRequest();
        repositoriesRequest.setUser(Settings.getInstance().getUser());

        BitbucketClient bitbucketClient = new BitbucketClient();
        bitbucketClient.setAccessToken(accessToken);
        bitbucketClient.setResource(repositoriesRequest);
        bitbucketClient.setHttpClientFactory(new DefaultHttpClientFactory());
        return bitbucketClient.execute(RepositoriesResponse.class);
    }
}
