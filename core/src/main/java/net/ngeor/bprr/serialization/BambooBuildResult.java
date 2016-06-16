package net.ngeor.bprr.serialization;

public class BambooBuildResult {
    private String key;
    private String lifeCycleState;
    private String state;
    private Link link;
    private int buildDurationInSeconds;
    private int successfulTestCount;
    private int skippedTestCount;

    public String getKey() {
        return key;
    }

    public String getLifeCycleState() {
        return lifeCycleState;
    }

    public String getState() {
        return state;
    }

    public Link getLink() {
        return link;
    }

    public int getBuildDurationInSeconds() {
        return buildDurationInSeconds;
    }

    public int getSuccessfulTestCount() {
        return successfulTestCount;
    }

    public int getSkippedTestCount() {
        return skippedTestCount;
    }
}
