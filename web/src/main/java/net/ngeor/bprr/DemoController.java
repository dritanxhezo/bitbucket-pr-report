package net.ngeor.bprr;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import net.ngeor.bprr.views.PullRequestsView;

/**
 * Demo controller.
 */
interface DemoController {
    PullRequestsView createView(HttpServletRequest req) throws IOException;
}
