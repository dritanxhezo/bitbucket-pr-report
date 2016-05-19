package net.ngeor.bprr;

/**
 * Provides concrete implementation for the interfaces of this package.
 */
class Factory {
    private Factory() {

    }

    public static final Factory Instance = new Factory();

    public DemoController demoController() {
        return new DefaultDemoController(bitbucketClientFactory(), teamMapper());
    }

    public BitbucketClientFactory bitbucketClientFactory() {
        return new DefaultBitbucketClientFactory();
    }

    public TeamMapper teamMapper() {
        DefaultTeamMapper result = new DefaultTeamMapper();
        result.loadFromProperties();
        return result;
    }
}
