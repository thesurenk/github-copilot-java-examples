package com.github.sample;

import com.github.copilot.CopilotClient;
import com.github.copilot.ConnectionState;
import com.github.copilot.rpc.MessageOptions;
import com.github.copilot.rpc.PermissionHandler;
import com.github.copilot.rpc.SessionConfig;

/**
 * Sends a single prompt to Copilot and waits for the assistant reply.
 */
public final class CopilotAskService {

    private static final String DEFAULT_MODEL = "auto";
    private static final long TIMEOUT_MS = 60_000L;

    private CopilotAskService() {
    }

    public static AskResult ask(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            return AskResult.failure(prompt, "Prompt is required.");
        }

        try (var client = CopilotClientFactory.create()) {
            client.start().get();

            if (client.getState() != ConnectionState.CONNECTED) {
                return AskResult.failure(prompt, "Copilot client is not connected.");
            }

            var auth = client.getAuthStatus().get();
            if (!auth.isAuthenticated()) {
                String hint = EnvConfig.hasGitHubToken()
                        ? "Token is configured but Copilot CLI reports not authenticated. Check token validity."
                        : "Copilot CLI is not authenticated. Add COPILOT_GITHUB_TOKEN to .env or run `copilot` to sign in.";
                return AskResult.failure(prompt, hint);
            }

            var session = client.createSession(new SessionConfig()
                    .setOnPermissionRequest(PermissionHandler.APPROVE_ALL)
                    .setModel(DEFAULT_MODEL)).get();

            try (session) {
                var response = session.sendAndWait(
                        new MessageOptions().setPrompt(prompt.trim()),
                        TIMEOUT_MS).get();
                String content = response.getData() != null ? response.getData().content() : null;
                if (content == null || content.isBlank()) {
                    return AskResult.failure(prompt, "Copilot returned an empty response.");
                }
                return AskResult.success(prompt.trim(), content);
            }
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            String message = cause.getMessage() != null
                    ? cause.getMessage()
                    : cause.getClass().getSimpleName();
            return AskResult.failure(prompt, message);
        }
    }
}
