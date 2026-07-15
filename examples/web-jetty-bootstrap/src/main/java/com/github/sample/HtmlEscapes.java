package com.github.sample;

/**
 * HTML escaping helpers for servlet templates.
 */
public final class HtmlEscapes {

    private HtmlEscapes() {
    }

    public static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
