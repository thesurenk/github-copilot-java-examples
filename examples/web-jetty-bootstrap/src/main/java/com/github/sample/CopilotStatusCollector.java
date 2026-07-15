package com.github.sample;

import com.github.copilot.CopilotClient;
import com.github.copilot.ConnectionState;

/**
 * Collects connectivity, auth, and version information from the Copilot SDK.
 */
public final class CopilotStatusCollector {

    private CopilotStatusCollector() {
    }

    public static StatusSnapshot collect() {
        VersionInfo versions = VersionInfo.current();
        try (var client = CopilotClientFactory.create()) {
            client.start().get();

            if (client.getState() != ConnectionState.CONNECTED) {
                return new StatusSnapshot(
                        false,
                        "Client state is " + client.getState() + " after start()",
                        AuthStatus.unknown("Connectivity check did not complete"),
                        versions);
            }

            var pong = client.ping("connectivity-check").get();
            String detail = "Connected - protocol version: " + pong.protocolVersion()
                    + ", message: " + pong.message();
            AuthStatus auth = readAuthStatus(client);
            return new StatusSnapshot(true, detail, auth, versions);
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            String message = cause.getMessage() != null
                    ? cause.getMessage()
                    : cause.getClass().getSimpleName();
            return new StatusSnapshot(false, message, AuthStatus.unknown("Auth check skipped"), versions);
        }
    }

    private static AuthStatus readAuthStatus(CopilotClient client) {
        try {
            var auth = client.getAuthStatus().get();
            String detail = auth.getStatusMessage();
            if (detail == null || detail.isBlank() || isGenericNotAuthenticated(detail)) {
                if (auth.isAuthenticated() && auth.getLogin() != null && !auth.getLogin().isBlank()) {
                    detail = "Signed in as " + auth.getLogin();
                } else if (auth.isAuthenticated()) {
                    detail = "Authenticated via " + nullToDash(auth.getAuthType());
                } else if (EnvConfig.hasGitHubToken()) {
                    detail = "Token is configured (" + EnvConfig.authSourceLabel()
                            + ") but Copilot CLI reports not authenticated. Check token validity and Copilot access.";
                } else {
                    detail = "Not authenticated. Add COPILOT_GITHUB_TOKEN to .env or run `copilot` to sign in.";
                }
            }
            return new AuthStatus(
                    true,
                    auth.isAuthenticated(),
                    auth.getAuthType(),
                    auth.getLogin(),
                    detail);
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            String message = cause.getMessage() != null
                    ? cause.getMessage()
                    : cause.getClass().getSimpleName();
            return AuthStatus.unknown("Auth check failed: " + message);
        }
    }

    private static String nullToDash(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

    private static boolean isGenericNotAuthenticated(String message) {
        return "not authenticated".equalsIgnoreCase(message.trim());
    }
}
