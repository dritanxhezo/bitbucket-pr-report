package net.ngeor.testutil;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.mockito.ArgumentMatcher;

import java.net.URI;

public class HttpGetMatcher extends ArgumentMatcher<HttpUriRequest> {
    private final URI expectedURI;

    public HttpGetMatcher(URI expectedURI) {
        this.expectedURI = expectedURI;
    }

    @Override
    public boolean matches(Object o) {
        if (!(o instanceof HttpGet)) {
            return false;
        }

        HttpGet that = (HttpGet) o;
        return expectedURI.equals(that.getURI());
    }
}
