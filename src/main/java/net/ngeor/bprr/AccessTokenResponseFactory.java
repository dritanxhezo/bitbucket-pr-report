package net.ngeor.bprr;

import com.google.gson.Gson;

import java.io.Reader;

public class AccessTokenResponseFactory implements ResponseFactory<AccessTokenResponse> {
    @Override
    public AccessTokenResponse parse(Reader reader) {
        Gson gson = new Gson();
        return gson.fromJson(reader, AccessTokenResponse.class);
    }
}
