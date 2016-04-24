package net.ngeor.bprr;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DemoServlet extends HttpServlet {
    private final DemoController controller;

    public DemoServlet()
    {
        this(new DefaultDemoController());
    }

    public DemoServlet(DemoController controller) {
        super();
        this.controller = controller;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accessToken = (String)req.getSession().getAttribute("accessToken");
        this.controller.setAccessToken(accessToken);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/demo.jsp");
        req.setAttribute("pullRequests", this.controller.loadPullRequests());
        requestDispatcher.forward(req, resp);
    }
}
