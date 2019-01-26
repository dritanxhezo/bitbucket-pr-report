package net.ngeor.bprr;

import java.io.IOException;

import net.ngeor.util.ResourceLoader;
import net.ngeor.util.ResourceLoaderImpl;

/**
 * Provides concrete implementation for the interfaces of this package.
 */
final class Factory {
    public static final Factory INSTANCE = new Factory();

    private Factory() {
    }

    /**
     * Creates the demo controller.
     * @return
     */
    public DemoController demoController() {
        try {
            return new DemoControllerImpl(pullRequestClient(), teamMapper());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RestClient bitbucketClient() throws IOException {
        return new BitbucketClientImpl(simpleHttpClient(), settings().getSecret());
    }

    public SimpleHttpClient simpleHttpClient() {
        return new SimpleHttpClientImpl();
    }

    /**
     * Creates the team maper.
     * @return
     */
    public TeamMapper teamMapper() {
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

    public PullRequestClient pullRequestClient() throws IOException {
        return new PullRequestClientImpl(bitbucketClient());
    }
}
