package net.ngeor.bprr;

import net.ngeor.util.ResourceLoader;
import net.ngeor.util.ResourceLoaderImpl;

import java.io.IOException;

/**
 * Provides concrete implementation for the interfaces of this package.
 */
class Factory {
    private Factory() {

    }

    public static final Factory Instance = new Factory();

    public DemoController demoController() {
        try {
            return new DemoControllerImpl(pullRequestClient(), teamMapper());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RestClient bitbucketClient() throws IOException {
        return new BitbucketClientImpl(httpClientFactory(), settings().getSecret());
    }

    public HttpClientFactory httpClientFactory() {
        return new HttpClientFactoryImpl();
    }

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
