package com.github.sample;

/**
 * Full application status for the home page and JSON API.
 */
public record StatusSnapshot(
        boolean connected,
        String message,
        AuthStatus auth,
        VersionInfo versions) {

    public String statusLabel() {
        return connected ? "PASS" : "FAIL";
    }

    public String statusClass() {
        return connected ? "pass" : "fail";
    }
}
