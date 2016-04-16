package net.ngeor.bprr;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class DemoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accessToken = (String)req.getSession().getAttribute("accessToken");
        PrintWriter out = resp.getWriter();
        try {
            out.println(accessToken);
            test(out, accessToken);
        } finally {
            out.flush();
            out.close();
        }
    }

    private void test(PrintWriter out, String accessToken) throws IOException {
        if (accessToken == null) {
            throw new IllegalStateException("No accessToken!");
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("https://api.bitbucket.org/2.0/repositories/" + Settings.getInstance().getUser() + "?access_token=" + accessToken);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            try {
                out.println(httpResponse.getStatusLine().getStatusCode());
                InputStream content = httpResponse.getEntity().getContent();
                InputStreamReader reader = new InputStreamReader(content);
                JsonStreamParser p = new JsonStreamParser(reader);
                while (p.hasNext()) {
                    JsonElement jsonElement = p.next();
                    out.println(jsonElement);
                    JsonObject repositories = jsonElement.getAsJsonObject().getAsJsonObject("values");
                    out.println(repositories);
                }

            } finally {
                httpResponse.close();
            }
        } finally {
            httpClient.close();
        }
    }
}
