package net.ngeor.bprr;

import com.google.gson.Gson;

import java.io.Reader;

public class PullRequestResponseFactory implements ResponseFactory<PullRequestResponse> {
    @Override
    public PullRequestResponse parse(Reader reader) {
        Gson gson = new Gson();
        return gson.fromJson(reader, PullRequestResponse.class);
    }
}