package net.ngeor.bprr;

import net.ngeor.dates.DateHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

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
        String[] parts = fullRepoName.split("\\/");
        this.controller.setUsername(parts[0]);
        this.controller.setRepository(parts[1]);
        try {
            this.controller.setUpdatedOn(DateHelper.parseUtcDate(req.getParameter("updatedOn")));
        } catch (ParseException e) {
            this.controller.setUpdatedOn(DateHelper.utcToday());
        }
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/demo.jsp");
        req.setAttribute("pullRequests", this.controller.loadPullRequests());
        requestDispatcher.forward(req, resp);
    }
}
