package net.ngeor.bprr;

import javax.servlet.http.HttpServletRequest;

public interface BitbucketClientFactory {
    BitbucketClient createClient(HttpServletRequest request);
}
