package net.ngeor.bprr;

import net.ngeor.bprr.views.PullRequestsView;
import net.ngeor.util.DateHelper;
import net.ngeor.util.DateRange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DemoServletTest {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private ServletConfig servletConfig;
    private ServletContext servletContext;
    private RequestDispatcher requestDispatcher;
    private DemoController demoController;
    private BitbucketClientFactory bitbucketClientFactory;
    private BitbucketClient bitbucketClient;
    private TeamMapper teamMapper;

    @Before
    public void before() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        servletConfig = mock(ServletConfig.class);
        servletContext = mock(ServletContext.class);
        requestDispatcher = mock(RequestDispatcher.class);
        demoController = mock(DemoController.class);
        bitbucketClientFactory = mock(BitbucketClientFactory.class);
        bitbucketClient = mock(BitbucketClient.class);
        teamMapper = mock(TeamMapper.class);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/WEB-INF/demo.jsp")).thenReturn(requestDispatcher);
        when(bitbucketClientFactory.createClient(req)).thenReturn(bitbucketClient);
    }

    @Test
    public void shouldUseController() throws ServletException, IOException {
        // arrange
        PullRequestModel pullRequests[] = new PullRequestModel[] {
            new PullRequestModel(123)
        };

        when(req.getParameter("repo")).thenReturn("ngeor/myproject");
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-05");
        when(demoController.loadPullRequests()).thenReturn(pullRequests);

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory, teamMapper);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(requestDispatcher).forward(req, resp);
        verify(demoController).setUsername("ngeor");
        verify(demoController).setRepository("myproject");
        verify(demoController).setUpdatedOn(new DateRange(DateHelper.utcDate(2016, 5, 5), DateHelper.utcToday()));
        verify(demoController).setBitbucketClient(bitbucketClient);

        ArgumentCaptor<PullRequestsView> argument = ArgumentCaptor.forClass(PullRequestsView.class);
        verify(req).setAttribute(eq("view"), argument.capture());
        PullRequestsView view = argument.getValue();
        assertArrayEquals(pullRequests, view.getPullRequests());
    }

    @Test
    public void shouldUseTeamMapper() throws ServletException, IOException {
        // arrange
        PullRequestModel pullRequests[] = new PullRequestModel[] {
                new PullRequestModel(123)
        };

        pullRequests[0].setAuthor("ngeor");

        when(req.getParameter("repo")).thenReturn("ngeor/myproject");
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-05");
        when(demoController.loadPullRequests()).thenReturn(pullRequests);

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory, teamMapper);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(teamMapper).assignTeams(pullRequests[0]);
    }

    @Test
    public void shouldUseCurrentDateWhenUpdatedOnParameterIsMissing() throws ServletException, IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("");

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory, teamMapper);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(demoController).setUpdatedOn(new DateRange(DateHelper.utcToday(), DateHelper.utcToday()));
    }

    @Test
    public void shouldUseUpdatedOnUtil() throws ServletException, IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-01");
        when(req.getParameter("updatedOnUntil")).thenReturn("2016-05-14");

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory, teamMapper);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(demoController).setUpdatedOn(new DateRange(DateHelper.utcDate(2016, 5, 1), DateHelper.utcDate(2016, 5, 14)));
    }

    @Test
    public void shouldSetFormUrlAttribute() throws ServletException, IOException {
        // arrange
        when(req.getRequestURI()).thenReturn("hello");

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory, teamMapper);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        ArgumentCaptor<PullRequestsView> argument = ArgumentCaptor.forClass(PullRequestsView.class);
        verify(req).setAttribute(eq("view"), argument.capture());
        PullRequestsView view = argument.getValue();
        assertEquals("hello", view.getFormUrl());
    }

    @Test
    public void shouldSetRepoAttribute() throws ServletException, IOException {
        // arrange
        when(req.getParameter("repo")).thenReturn("hello/abc");

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory, teamMapper);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        ArgumentCaptor<PullRequestsView> argument = ArgumentCaptor.forClass(PullRequestsView.class);
        verify(req).setAttribute(eq("view"), argument.capture());
        PullRequestsView view = argument.getValue();
        assertEquals("hello/abc", view.getRepo());
    }

    @Test
    public void shouldSetUpdatedOnAttribute() throws ServletException, IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn("2016-05-01");

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory, teamMapper);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        ArgumentCaptor<PullRequestsView> argument = ArgumentCaptor.forClass(PullRequestsView.class);
        verify(req).setAttribute(eq("view"), argument.capture());
        PullRequestsView view = argument.getValue();
        assertEquals("2016-05-01", view.getUpdatedOnFrom());
    }

    @Test
    public void shouldSetUpdatedOnAttributeWhenParameterIsMissing() throws ServletException, IOException {
        // arrange
        when(req.getParameter("updatedOnFrom")).thenReturn(null);

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory, teamMapper);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        ArgumentCaptor<PullRequestsView> argument = ArgumentCaptor.forClass(PullRequestsView.class);
        verify(req).setAttribute(eq("view"), argument.capture());
        PullRequestsView view = argument.getValue();
        assertEquals(DateHelper.formatDate(DateHelper.utcToday()), view.getUpdatedOnFrom());
    }
}
