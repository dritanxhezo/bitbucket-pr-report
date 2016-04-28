package net.ngeor.bprr;

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
    @Test
    public void shouldUseController() throws ServletException, IOException {
        // arrange
        PullRequestModel pullRequests[] = new PullRequestModel[] {
            new PullRequestModel(123, "description", "open", new Date(), new Date(), "author", "reviewer1", "reviewer2")
        };

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        ServletConfig servletConfig = mock(ServletConfig.class);
        ServletContext servletContext = mock(ServletContext.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        DemoController demoController = mock(DemoController.class);
        BitbucketClientFactory bitbucketClientFactory = mock(BitbucketClientFactory.class);
        BitbucketClient bitbucketClient = mock(BitbucketClient.class);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/WEB-INF/demo.jsp")).thenReturn(requestDispatcher);
        when(req.getParameter("repo")).thenReturn("ngeor/myproject");
        when(bitbucketClientFactory.createClient(req)).thenReturn(bitbucketClient);
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
        verify(demoController).setBitbucketClient(bitbucketClient);
    }
}
