package com.github.sample;

/**
 * Serializes {@link StatusSnapshot} to JSON without extra dependencies.
 */
public final class StatusJson {

    private StatusJson() {
    }

    public static String toJson(StatusSnapshot status) {
        StringBuilder json = new StringBuilder(256);
        json.append('{');
        appendField(json, "connected", status.connected());
        json.append(',');
        appendStringField(json, "message", status.message());
        json.append(',');
        json.append("\"auth\":{");
        appendField(json, "known", status.auth().known());
        json.append(',');
        appendField(json, "authenticated", status.auth().authenticated());
        json.append(',');
        appendStringField(json, "authType", status.auth().authType());
        json.append(',');
        appendStringField(json, "login", status.auth().login());
        json.append(',');
        appendStringField(json, "message", status.auth().message());
        json.append("},");
        json.append("\"versions\":{");
        appendStringField(json, "java", status.versions().javaVersion());
        json.append(',');
        appendStringField(json, "jetty", status.versions().jettyVersion());
        json.append(',');
        appendStringField(json, "copilotSdk", status.versions().copilotSdkVersion());
        json.append(',');
        appendStringField(json, "maven", status.versions().mavenVersion());
        json.append("}}");
        return json.toString();
    }

    private static void appendField(StringBuilder json, String name, boolean value) {
        json.append('"').append(name).append("\":").append(value);
    }

    private static void appendStringField(StringBuilder json, String name, String value) {
        json.append('"').append(name).append("\":");
        if (value == null) {
            json.append("null");
        } else {
            json.append('"').append(escape(value)).append('"');
        }
    }

    static String escape(String value) {
        StringBuilder escaped = new StringBuilder(value.length() + 8);
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '\\' -> escaped.append("\\\\");
                case '"' -> escaped.append("\\\"");
                case '\n' -> escaped.append("\\n");
                case '\r' -> escaped.append("\\r");
                case '\t' -> escaped.append("\\t");
                default -> escaped.append(ch);
            }
        }
        return escaped.toString();
    }
}
