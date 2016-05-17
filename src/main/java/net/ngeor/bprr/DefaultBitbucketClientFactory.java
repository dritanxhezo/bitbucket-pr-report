package net.ngeor.bprr;

import javax.servlet.http.HttpServletRequest;

public class DefaultBitbucketClientFactory implements BitbucketClientFactory {
    @Override
    public BitbucketClient createClient(HttpServletRequest request) {
        String accessToken = (String) request.getSession().getAttribute("accessToken");
        BitbucketClient result = new DefaultBitbucketClient();
        result.setAccessToken(accessToken);
        return result;
    }
}
