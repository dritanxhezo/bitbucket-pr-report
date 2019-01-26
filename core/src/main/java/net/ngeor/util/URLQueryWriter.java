package net.ngeor.util;

/**
 * Utility class for building query strings.
 */
public class URLQueryWriter {
    private final URLStringBuilder stringBuilder;
    private boolean needsAnd;

    public URLQueryWriter(URLStringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    /**
     * Writes the given key value.
     * @param key The key
     * @param operand The operand
     * @param value The value
     */
    public void write(String key, String operand, String value) {
        if (needsAnd) {
            stringBuilder.appendEncoded(" AND ");
        } else {
            needsAnd = true;
        }

        stringBuilder.appendEncoded(key).appendEncoded(" ").appendEncoded(operand).appendEncoded(" ").appendEncoded(
            value);
    }
}
