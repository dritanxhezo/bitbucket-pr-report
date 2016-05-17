package net.ngeor.bprr;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!shouldIntercept(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            RequestDispatcher requestDispatcher = servletRequest.getServletContext().getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean shouldIntercept(ServletRequest servletRequest) {
        if (servletRequest == null) {
            return false;
        }

        if (!(servletRequest instanceof HttpServletRequest)) {
            return false;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if (session == null) {
            return false;
        }

        String accessToken = (String) session.getAttribute("accessToken");
        if (accessToken != null && !accessToken.isEmpty()) {
            return false;
        }

        return true;
    }
}
