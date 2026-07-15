package com.github.copilot.examples.cliconnectivity;

import com.github.copilot.CopilotClient;
import com.github.copilot.rpc.CopilotClientOptions;

/**
 * Creates {@link CopilotClient} instances using env token or CLI login.
 */
public final class CopilotClientFactory {

    private CopilotClientFactory() {
    }

    public static CopilotClient create() {
        String token = EnvConfig.resolveGitHubToken();
        if (token != null) {
            return new CopilotClient(new CopilotClientOptions()
                    .setGitHubToken(token)
                    .setUseLoggedInUser(false));
        }
        return new CopilotClient();
    }

    static String resolveToken(String copilotGitHubToken, String ghToken, String githubToken) {
        return firstNonBlank(copilotGitHubToken, ghToken, githubToken);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
        }
        return null;
    }
}
