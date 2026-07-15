package com.github.sample;

import com.github.copilot.ConnectionState;

/**
 * Checks whether the GitHub Copilot CLI is reachable via the Java SDK.
 */
public final class ConnectivityChecker {

    private ConnectivityChecker() {
    }

    public static ConnectivityResult check() {
        StatusSnapshot status = CopilotStatusCollector.collect();
        return new ConnectivityResult(status.connected(), status.message());
    }

    /**
     * Legacy direct check used by integration tests.
     */
    static ConnectivityResult checkDirect() {
        try (var client = CopilotClientFactory.create()) {
            client.start().get();

            if (client.getState() != ConnectionState.CONNECTED) {
                return new ConnectivityResult(false,
                        "Client state is " + client.getState() + " after start()");
            }

            var pong = client.ping("connectivity-check").get();
            String detail = "Connected - protocol version: " + pong.protocolVersion()
                    + ", message: " + pong.message();
            return new ConnectivityResult(true, detail);
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            return new ConnectivityResult(false, cause.getMessage() != null
                    ? cause.getMessage()
                    : cause.getClass().getSimpleName());
        }
    }
}
