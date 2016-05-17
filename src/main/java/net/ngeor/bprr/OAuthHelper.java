package net.ngeor.bprr;

import javax.servlet.http.HttpServletRequest;

public class OAuthHelper {
    private final HttpServletRequest request;

    public OAuthHelper(HttpServletRequest request) {
        this.request = request;
    }

    public String getAccessToken() {
        return (String) this.request.getSession().getAttribute("accessToken");
    }
}
