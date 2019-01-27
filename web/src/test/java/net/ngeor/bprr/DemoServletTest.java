package net.ngeor.bprr;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ngeor.bprr.views.PullRequestsView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DemoServlet}.
 */
@ExtendWith(MockitoExtension.class)
class DemoServletTest {
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private DemoController demoController;
    @Mock
    private Factory factory;

    @BeforeEach
    void before() {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/WEB-INF/demo.jsp")).thenReturn(requestDispatcher);
        when(factory.demoController(req)).thenReturn(demoController);
    }

    @Test
    void shouldUseControllerToSetView() throws ServletException, IOException {
        // arrange
        PullRequestsView view = mock(PullRequestsView.class);
        when(demoController.createView(req)).thenReturn(view);

        // act
        DemoServlet servlet = new DemoServlet();
        servlet.setFactory(factory);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(req).setAttribute("view", view);
    }

    @Test
    void shouldRedirectResponse() throws ServletException, IOException {
        // arrange
        PullRequestsView view = mock(PullRequestsView.class);
        when(demoController.createView(req)).thenReturn(view);

        // act
        DemoServlet servlet = new DemoServlet();
        servlet.setFactory(factory);
        servlet.init(servletConfig);
        servlet.doGet(req, resp);

        // assert
        verify(requestDispatcher).forward(req, resp);
    }
}
