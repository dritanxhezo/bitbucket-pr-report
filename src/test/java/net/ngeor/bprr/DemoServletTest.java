package net.ngeor.bprr;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class DemoServletTest {
    @Test
    public void shouldUseController() throws ServletException, IOException {
        // arrange
        PullRequestModel pullRequests[] = new PullRequestModel[] {
            new PullRequestModel(123, "author", "reviewer1", "reviewer2")
        };

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        ServletConfig servletConfig = mock(ServletConfig.class);
        ServletContext servletContext = mock(ServletContext.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        DemoController demoController = mock(DemoController.class);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/WEB-INF/demo.jsp")).thenReturn(requestDispatcher);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("accessToken")).thenReturn("pass");
        when(demoController.loadPullRequests()).thenReturn(pullRequests);

        // act
        DemoServlet servlet = new DemoServlet(demoController);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(requestDispatcher).forward(req, resp);
        verify(req).setAttribute("pullRequests", pullRequests);
        verify(demoController).setAccessToken("pass");
    }
}
