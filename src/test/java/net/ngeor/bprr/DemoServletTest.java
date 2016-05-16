package net.ngeor.bprr;

import net.ngeor.bprr.views.PullRequestsView;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class DemoServletTest {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private ServletConfig servletConfig;
    private ServletContext servletContext;
    private RequestDispatcher requestDispatcher;
    private DemoController demoController;

    @Before
    public void before() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        servletConfig = mock(ServletConfig.class);
        servletContext = mock(ServletContext.class);
        requestDispatcher = mock(RequestDispatcher.class);
        demoController = mock(DemoController.class);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/WEB-INF/demo.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void shouldUseControllerToSetView() throws ServletException, IOException {
        // arrange
        PullRequestsView view = mock(PullRequestsView.class);
        when(demoController.createView(req)).thenReturn(view);

        // act
        DemoServlet servlet = new DemoServlet(demoController);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(req).setAttribute("view", view);
    }

    @Test
    public void shouldRedirectResponse() throws ServletException, IOException {
        // arrange
        PullRequestsView view = mock(PullRequestsView.class);
        when(demoController.createView(req)).thenReturn(view);

        // act
        DemoServlet servlet = new DemoServlet(demoController);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(requestDispatcher).forward(req, resp);
    }
}
