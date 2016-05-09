package net.ngeor.bprr;

import net.ngeor.util.DateHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class DemoServlet extends HttpServlet {
    private final DemoController controller;
    private final BitbucketClientFactory bitbucketClientFactory;

    public DemoServlet()
    {
        this(new DefaultDemoController(), new DefaultBitbucketClientFactory());
    }

    public DemoServlet(DemoController controller, BitbucketClientFactory bitbucketClientFactory) {
        super();
        this.controller = controller;
        this.bitbucketClientFactory = bitbucketClientFactory;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BitbucketClient bitbucketClient = bitbucketClientFactory.createClient(req);
        this.controller.setBitbucketClient(bitbucketClient);
        String fullRepoName = req.getParameter("repo");
        if (fullRepoName != null) {
            String[] parts = fullRepoName.split("\\/");
            this.controller.setUsername(parts[0]);
            this.controller.setRepository(parts[1]);
        }

        Date updatedOn;
        try {
            updatedOn = DateHelper.parseUtcDate(req.getParameter("updatedOn"));
        } catch (ParseException e) {
            updatedOn = DateHelper.utcToday();
        }

        this.controller.setUpdatedOn(updatedOn);

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/demo.jsp");
        req.setAttribute("pullRequests", this.controller.loadPullRequests());
        req.setAttribute("formurl", req.getRequestURI());
        req.setAttribute("repo", fullRepoName);
        req.setAttribute("updatedOn", DateHelper.formatDate(updatedOn));
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
