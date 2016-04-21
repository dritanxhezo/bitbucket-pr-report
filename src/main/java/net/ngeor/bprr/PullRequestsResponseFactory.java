package net.ngeor.bprr;

import com.google.gson.Gson;

import java.io.Reader;

public class PullRequestsResponseFactory implements ResponseFactory<PullRequestsResponse> {
    @Override
    public PullRequestsResponse parse(Reader reader) {
        Gson gson = new Gson();
        return gson.fromJson(reader, PullRequestsResponse.class);
    }
}