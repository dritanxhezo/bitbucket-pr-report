package net.ngeor.bamboo;

import net.ngeor.bitbucket.Link;

/**
 * A build result from Bamboo.
 */
public class BuildResult {
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

    /**
     * Gets the URL of a log file.
     * @param jobName The job name.
     * @return The URL to the log file.
     */
    public String getLogFileUrl(String jobName) {
        // buildResultLink =
        // https://company.jira.com/builds/rest/api/latest/result/PRJ-PLN-1031
        // desired result  =
        // https://company.jira.com/builds/download/PRJ-PLN-JOB1/build_logs/PRJ-PLN-JOB1-1031.log
        String buildResultLink      = getLink().getHref();
        final String restApiSlug    = "rest/api/latest/result/";
        int idx                     = buildResultLink.indexOf(restApiSlug);
        String beforeRestApiSlug    = buildResultLink.substring(0, idx);
        String buildResultId        = buildResultLink.substring(idx + restApiSlug.length());
        String[] buildResultIdParts = buildResultId.split("-");
        String projectKey           = buildResultIdParts[0];
        String planKey              = buildResultIdParts[1];
        String buildNumber          = buildResultIdParts[2];
        String jobId                = projectKey + "-" + planKey + "-" + jobName;
        String result = beforeRestApiSlug + "download/" + jobId + "/build_logs/" + jobId + "-" + buildNumber + ".log";
        return result;
    }
}
