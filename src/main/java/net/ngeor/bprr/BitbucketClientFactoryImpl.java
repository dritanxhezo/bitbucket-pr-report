package net.ngeor.bprr;

import javax.servlet.http.HttpServletRequest;

public class BitbucketClientFactoryImpl implements BitbucketClientFactory {
    @Override
    public BitbucketClient createClient(HttpServletRequest request) {
        String accessToken = (String) request.getSession().getAttribute("accessToken");
        BitbucketClient result = new BitbucketClientImpl();
        result.setAccessToken(accessToken);
        return result;
    }
}
