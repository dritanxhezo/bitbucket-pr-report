package net.ngeor.bprr;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ngeor.bitbucket.Repository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link HomeServlet}.
 */
@ExtendWith(MockitoExtension.class)
class HomeServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Factory factory;

    @Mock
    private HttpSession session;

    @Mock
    private BitbucketClient bitbucketClient;

    @Mock
    private ServletContext servletContext;

    @Mock
    private RequestDispatcher requestDispatcher;

    private HomeServlet homeServlet;

    @BeforeEach
    void beforeEach() {
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        homeServlet = new HomeServlet();
        homeServlet.setFactory(factory);
    }

    @Test
    void loggedIn() throws IOException, ServletException {
        // arrange
        when(session.getAttribute("owner")).thenReturn("acme");
        when(factory.bitbucketClient(request)).thenReturn(bitbucketClient);
        Repository repository = mock(Repository.class);
        when(bitbucketClient.getAllRepositories()).thenReturn(Collections.singletonList(repository));

        // act
        homeServlet.doGet(request, response);

        // assert
        verify(request).setAttribute("repositories", Collections.singletonList(repository));
        verify(servletContext).getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void loggedOut() throws IOException, ServletException {
        // act
        homeServlet.doGet(request, response);

        // assert
        verify(servletContext).getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}
