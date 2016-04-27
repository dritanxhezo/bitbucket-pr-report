package net.ngeor.bprr;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OAuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String key = Settings.getInstance().getClientId();
        String secret = Settings.getInstance().getSecret();

        if (code == null) {
            throw new IllegalStateException("No code!");
        }

        HttpHost targetHost = new HttpHost("bitbucket.org", 443, "https");
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials(key, secret)
        );

        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        final HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credentialsProvider);
        context.setAuthCache(authCache);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost("https://bitbucket.org/site/oauth2/access_token");
            List<NameValuePair> formData = new ArrayList<>();
            formData.add(new BasicNameValuePair("grant_type", "authorization_code"));
            formData.add(new BasicNameValuePair("code", code));
            httpPost.setEntity(new UrlEncodedFormEntity(formData));

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost, context);
            try {
                InputStream content = httpResponse.getEntity().getContent();
                InputStreamReader reader = new InputStreamReader(content);
                Gson gson = new Gson();
                AccessTokenResponse accessTokenResponse = gson.fromJson(reader, AccessTokenResponse.class);
                String accessToken = accessTokenResponse.getAccessToken();
                req.getSession().setAttribute("accessToken", accessToken);

                String redirectUrl = getRedirectUrl(req);
                resp.sendRedirect(redirectUrl);
            } finally {
                httpResponse.close();
            }
        } finally {
            httpClient.close();
        }
    }

    private String getRedirectUrl(HttpServletRequest req) {
        String requestUri = req.getRequestURI(); // /bprr/oauth
        String servletPath = req.getServletPath(); // /oauth
        String result = requestUri.substring(0, requestUri.length() - servletPath.length());
        return result;
    }
}