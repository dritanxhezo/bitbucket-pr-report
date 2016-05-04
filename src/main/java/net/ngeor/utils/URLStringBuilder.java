package net.ngeor.utils;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLStringBuilder implements Appendable {
    private final StringBuilder backend;

    public URLStringBuilder() {
        this(new StringBuilder());
    }

    public URLStringBuilder(@NotNull StringBuilder backend) {
        this.backend = backend;
    }

    @Override
    public URLStringBuilder append(CharSequence csq) {
        backend.append(csq);
        return this;
    }

    @Override
    public URLStringBuilder append(CharSequence csq, int start, int end) {
        backend.append(csq, start, end);
        return this;
    }

    @Override
    public URLStringBuilder append(char c) {
        backend.append(c);
        return this;
    }

    @Override
    public String toString() {
        return backend.toString();
    }

    public URLStringBuilder appendEncoded(String value) {
        return this.append(urlEncode(value));
    }

    private static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
