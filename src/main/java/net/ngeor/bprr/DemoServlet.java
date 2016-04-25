package net.ngeor.bprr;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DemoServlet extends HttpServlet {
    private final DemoController controller;

    public DemoServlet()
    {
        this(new DefaultDemoController());
    }

    public DemoServlet(DemoController controller) {
        super();
        this.controller = controller;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accessToken = (String)req.getSession().getAttribute("accessToken");

        // TODO: create BitbucketClient provider
        BitbucketClient bitbucketClient = new DefaultBitbucketClient();
        bitbucketClient.setAccessToken(accessToken);
        this.controller.setBitbucketClient(bitbucketClient);
        String fullRepoName = req.getParameter("repo");
        String[] parts = fullRepoName.split("\\/");
        this.controller.setUsername(parts[0]);
        this.controller.setRepository(parts[1]);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/demo.jsp");
        req.setAttribute("pullRequests", this.controller.loadPullRequests());
        requestDispatcher.forward(req, resp);
    }
}
