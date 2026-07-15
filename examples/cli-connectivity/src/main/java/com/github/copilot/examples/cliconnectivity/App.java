package com.github.copilot.examples.cliconnectivity;

import com.github.copilot.CopilotClient;
import com.github.copilot.ConnectionState;

import java.nio.file.Path;

/**
 * Minimal CLI connectivity and auth check for the GitHub Copilot Java SDK.
 */
public final class App {

    private App() {
    }

    public static void main(String[] args) throws Exception {
        EnvConfig.loadDotEnv(Path.of(".env"));
        System.out.println("Auth source: " + EnvConfig.authSourceLabel());

        try (var client = CopilotClientFactory.create()) {
            client.start().get();

            if (client.getState() != ConnectionState.CONNECTED) {
                System.out.println("Connectivity: FAIL - Client state is " + client.getState());
                System.exit(1);
            }

            var pong = client.ping("connectivity-check").get();
            System.out.println("Connectivity: PASS - Connected - protocol version: "
                    + pong.protocolVersion() + ", message: " + pong.message());

            var auth = client.getAuthStatus().get();
            String authLabel = auth.isAuthenticated() ? "SIGNED IN" : "NOT SIGNED IN";
            String authDetail = auth.getStatusMessage();
            if (authDetail == null || authDetail.isBlank()) {
                if (auth.isAuthenticated() && auth.getLogin() != null && !auth.getLogin().isBlank()) {
                    authDetail = "Signed in as " + auth.getLogin();
                } else if (auth.isAuthenticated()) {
                    authDetail = "Authenticated via " + auth.getAuthType();
                } else if (EnvConfig.hasGitHubToken()) {
                    authDetail = "Token configured but CLI reports not authenticated";
                } else {
                    authDetail = "Run `copilot` or set COPILOT_GITHUB_TOKEN in .env";
                }
            }
            System.out.println("Authentication: " + authLabel + " - " + authDetail);

            System.exit(auth.isAuthenticated() ? 0 : 2);
        }
    }
}
