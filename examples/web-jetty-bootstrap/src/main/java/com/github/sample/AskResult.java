package com.github.sample;

/**
 * Outcome of a single Copilot prompt round-trip.
 */
public record AskResult(boolean success, String prompt, String reply, String error) {

    public static AskResult success(String prompt, String reply) {
        return new AskResult(true, prompt, reply, null);
    }

    public static AskResult failure(String prompt, String error) {
        return new AskResult(false, prompt, null, error);
    }

    public String statusClass() {
        return success ? "pass" : "fail";
    }
}
