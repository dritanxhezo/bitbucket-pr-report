package net.ngeor.bprr;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DemoServlet extends HttpServlet {
    private final DemoController controller;

    public DemoServlet() {
        this(Factory.Instance.demoController());
    }

    public DemoServlet(DemoController controller) {
        super();
        this.controller = controller;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("view", this.controller.createView(req));

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/demo.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}