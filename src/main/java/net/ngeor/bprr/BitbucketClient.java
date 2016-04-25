package net.ngeor.bprr;

import java.io.IOException;

public interface BitbucketClient {
    void setAccessToken(String accessToken);
    <E> E execute(Object resource, Class<E> responseType) throws IOException;
}
