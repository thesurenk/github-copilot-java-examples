package com.github.sample;

/**
 * Copilot CLI authentication state.
 */
public record AuthStatus(boolean known, boolean authenticated, String authType, String login, String message) {

    public static AuthStatus unknown(String message) {
        return new AuthStatus(false, false, null, null, message);
    }

    public String statusLabel() {
        if (!known) {
            return "UNKNOWN";
        }
        return authenticated ? "SIGNED IN" : "NOT SIGNED IN";
    }

    public String statusClass() {
        if (!known) {
            return "neutral";
        }
        return authenticated ? "pass" : "fail";
    }
}
