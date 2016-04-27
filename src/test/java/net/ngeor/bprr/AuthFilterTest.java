package net.ngeor.bprr;

import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class AuthFilterTest {
    @Test
    public void shouldNotInterceptNullRequest() throws IOException, ServletException {
        AuthFilter authFilter = new AuthFilter();
        ServletRequest servletRequest = null;
        ServletResponse servletResponse = mock(ServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        authFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void shouldNotInterceptNonHttpRequest() throws IOException, ServletException {
        AuthFilter authFilter = new AuthFilter();
        ServletRequest servletRequest = mock(ServletRequest.class);
        ServletResponse servletResponse = mock(ServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        authFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void shouldNotInterceptHttpRequestWithoutSession() throws IOException, ServletException {
        AuthFilter authFilter = new AuthFilter();
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        ServletResponse servletResponse = mock(ServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        authFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void shouldInterceptHttpRequestWithSession() throws IOException, ServletException {
        AuthFilter authFilter = new AuthFilter();
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(servletRequest.getSession()).thenReturn(session);
        ServletResponse servletResponse = mock(ServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        authFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain, never()).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void shouldNotInterceptWhenAccessTokenIsInSession() throws IOException, ServletException {
        AuthFilter authFilter = new AuthFilter();
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(servletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("accessToken")).thenReturn("pass123");
        ServletResponse servletResponse = mock(ServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        authFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }
}
