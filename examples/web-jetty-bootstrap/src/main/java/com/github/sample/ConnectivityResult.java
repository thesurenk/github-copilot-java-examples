package com.github.sample;

/**
 * Outcome of a Copilot CLI connectivity check.
 */
public record ConnectivityResult(boolean connected, String message) {

    public String statusLabel() {
        return connected ? "PASS" : "FAIL";
    }
}
