package net.ngeor.bprr;

public class BitbucketClientImpl extends RestClientImpl {
    public BitbucketClientImpl(HttpClientFactory httpClientFactory, Settings settings) {
        super(httpClientFactory, settings);
    }

    @Override
    protected String createUrl(Object resource) {
        if (isBitbucketUrl(resource)) {
            return (String) resource;
        }

        StringBuilder result = new StringBuilder();
        result.append("https://api.bitbucket.org/2.0/");
        result.append(resource);
        return result.toString();
    }

    private boolean isBitbucketUrl(Object resource) {
        if (!(resource instanceof String)) {
            return false;
        }

        String url = (String) resource;
        return url.startsWith("https://api.bitbucket.org/2.0/");
    }
}
