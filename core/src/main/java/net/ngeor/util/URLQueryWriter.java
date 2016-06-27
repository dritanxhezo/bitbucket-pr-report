package net.ngeor.util;

public class URLQueryWriter {
    private final URLStringBuilder stringBuilder;
    private boolean needsAnd;

    public URLQueryWriter(URLStringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public void write(String key, String operand, String value) {
        if (needsAnd) {
            stringBuilder.appendEncoded(" AND ");
        } else {
            needsAnd = true;
        }

        stringBuilder.appendEncoded(key).appendEncoded(" ")
            .appendEncoded(operand).appendEncoded(" ")
            .appendEncoded(value);
    }

}
