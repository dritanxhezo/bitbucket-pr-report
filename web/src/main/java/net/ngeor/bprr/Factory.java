package net.ngeor.bprr;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.ngeor.http.HttpClient;
import net.ngeor.http.HttpClientImpl;
import net.ngeor.http.JsonHttpClient;
import net.ngeor.http.JsonHttpClientImpl;
import net.ngeor.util.ResourceLoader;
import net.ngeor.util.ResourceLoaderImpl;

/**
 * Provides concrete implementation for the interfaces of this package.
 */
class Factory {
    public static final Factory INSTANCE = new Factory();

    /**
     * Creates the demo controller.
     * @return
     */
    public DemoController demoController(HttpServletRequest request) {
        try {
            return new DemoControllerImpl(pullRequestClient(request), teamMapper());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the team maper.
     * @return
     */
    public TeamMapper teamMapper() throws IOException {
        TeamMapperImpl result = new TeamMapperImpl(resourceLoader());
        result.loadFromProperties();
        return result;
    }

    public Settings settings() throws IOException {
        return new SettingsImpl(resourceLoader());
    }

    public ResourceLoader resourceLoader() {
        return new ResourceLoaderImpl();
    }

    public PullRequestClient pullRequestClient(HttpServletRequest request) {
        return new PullRequestClientImpl(jsonHttpClient(request));
    }

    /**
     * Creates the HTTP client.
     * @param request The request holds the credentials in the session.
     * @return An instance of the HTTP client.
     */
    public HttpClient httpClient(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username     = (String) session.getAttribute("username");
        String password     = (String) session.getAttribute("password");
        return new HttpClientImpl(username, password);
    }

    public JsonHttpClient jsonHttpClient(HttpServletRequest request) {
        return new JsonHttpClientImpl(httpClient(request));
    }

    /**
     * Creates the Bitbucket client.
     * @param request The request holds the owner in the session.
     */
    public BitbucketClient bitbucketClient(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String owner        = (String) session.getAttribute("owner");
        return new BitbucketClient(jsonHttpClient(request), owner);
    }
}
