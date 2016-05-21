package net.ngeor.bprr;

import net.ngeor.bprr.views.PullRequestsView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

interface DemoController {
    PullRequestsView createView(HttpServletRequest req) throws IOException;
}

