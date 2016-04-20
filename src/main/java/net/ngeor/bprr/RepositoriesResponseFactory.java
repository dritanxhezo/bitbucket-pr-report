package net.ngeor.bprr;

import com.google.gson.Gson;

import java.io.Reader;

public class RepositoriesResponseFactory implements ResponseFactory<RepositoriesResponse> {
    @Override
    public RepositoriesResponse parse(Reader reader) {
        Gson gson = new Gson();
        return gson.fromJson(reader, RepositoriesResponse.class);
    }
}
