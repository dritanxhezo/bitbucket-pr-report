package net.ngeor.bprr;

import net.ngeor.dates.DateHelper;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

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

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/WEB-INF/demo.jsp")).thenReturn(requestDispatcher);
        when(bitbucketClientFactory.createClient(req)).thenReturn(bitbucketClient);
    }

    @Test
    public void shouldUseController() throws ServletException, IOException {
        // arrange
        PullRequestModel pullRequests[] = new PullRequestModel[] {
            new PullRequestModel(123, "description", "open", new Date(), new Date(), "author", "reviewer1", "reviewer2")
        };

        when(req.getParameter("repo")).thenReturn("ngeor/myproject");
        when(req.getParameter("updatedOn")).thenReturn("2016-05-05");
        when(demoController.loadPullRequests()).thenReturn(pullRequests);

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(requestDispatcher).forward(req, resp);
        verify(req).setAttribute("pullRequests", pullRequests);
        verify(demoController).setUsername("ngeor");
        verify(demoController).setRepository("myproject");
        verify(demoController).setUpdatedOn(DateHelper.utcDate(2016, 5, 5));
        verify(demoController).setBitbucketClient(bitbucketClient);
    }

    @Test
    public void shouldUseCurrentDateWhenUpdatedOnParameterIsMissing() throws ServletException, IOException {
        // arrange
        when(req.getParameter("updatedOn")).thenReturn("");

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(demoController).setUpdatedOn(DateHelper.utcToday());
    }

    @Test
    public void shouldSetFormUrlAttribute() throws ServletException, IOException {
        // arrange
        when(req.getRequestURI()).thenReturn("hello");

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(req).setAttribute("formurl", "hello");
    }

    @Test
    public void shouldSetRepoAttribute() throws ServletException, IOException {
        // arrange
        when(req.getParameter("repo")).thenReturn("hello/abc");

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(req).setAttribute("repo", "hello/abc");
    }

    @Test
    public void shouldSetUpdatedOnAttribute() throws ServletException, IOException {
        // arrange
        when(req.getParameter("updatedOn")).thenReturn("2016-05-01");

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(req).setAttribute("updatedOn", "2016-05-01");
    }

    @Test
    public void shouldSetUpdatedOnAttributeWhenParameterIsMissing() throws ServletException, IOException {
        // arrange
        when(req.getParameter("updatedOn")).thenReturn(null);

        // act
        DemoServlet servlet = new DemoServlet(demoController, bitbucketClientFactory);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(req).setAttribute("updatedOn", DateHelper.formatDate(DateHelper.utcToday()));
    }
}
