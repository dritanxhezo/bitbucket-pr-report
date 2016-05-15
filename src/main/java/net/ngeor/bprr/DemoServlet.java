package net.ngeor.bprr;

import net.ngeor.bprr.views.PullRequestsView;
import net.ngeor.util.DateHelper;
import net.ngeor.util.DateRange;

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
    private final TeamMapper teamMapper;

    public DemoServlet()
    {
        this(new DefaultDemoController(), new DefaultBitbucketClientFactory(), new DefaultTeamMapper());
    }

    public DemoServlet(DemoController controller, BitbucketClientFactory bitbucketClientFactory, TeamMapper teamMapper) {
        super();
        this.controller = controller;
        this.bitbucketClientFactory = bitbucketClientFactory;
        this.teamMapper = teamMapper;
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

        Date updatedOnFrom;
        try {
            updatedOnFrom = DateHelper.parseUtcDate(req.getParameter("updatedOnFrom"));
        } catch (ParseException e) {
            updatedOnFrom = DateHelper.utcToday();
        }

        Date updatedOnUntil;
        try {
            updatedOnUntil = DateHelper.parseUtcDate(req.getParameter("updatedOnUntil"));
        } catch (ParseException e) {
            updatedOnUntil = DateHelper.utcToday();
        }

        this.controller.setUpdatedOn(new DateRange(updatedOnFrom, updatedOnUntil));

        PullRequestModel[] pullRequests = this.controller.loadPullRequests();
        if (pullRequests != null) {
            this.teamMapper.loadFromProperties();

            for (PullRequestModel pullRequestModel : pullRequests) {
                this.teamMapper.assignTeams(pullRequestModel);
            }
        }

        PullRequestsView view = new PullRequestsView();
        view.setFormUrl(req.getRequestURI());
        view.setRepo(fullRepoName);
        view.setPullRequests(pullRequests);
        view.setUpdatedOnFrom(DateHelper.formatDate(updatedOnFrom));
        view.setUpdatedOnUntil(DateHelper.formatDate(updatedOnUntil));
        req.setAttribute("view", view);

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/demo.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
