package net.ngeor.bprr;

public interface DemoController {
    void setAccessToken(String accessToken);
    PullRequestModel[] loadPullRequests();
}

class DefaultDemoController implements DemoController {

    @Override
    public void setAccessToken(String accessToken) {

    }

    @Override
    public PullRequestModel[] loadPullRequests() {
        return new PullRequestModel[0];
    }
}