package com.github.sample;

import com.github.copilot.CopilotClient;
import com.github.copilot.rpc.CopilotClientOptions;

/**
 * Creates {@link CopilotClient} instances using safe, beginner-friendly auth options.
 * <p>
 * Priority:
 * <ol>
 *   <li>Environment token: {@code COPILOT_GITHUB_TOKEN}, then {@code GH_TOKEN}, then {@code GITHUB_TOKEN}</li>
 *   <li>Otherwise: use Copilot CLI login stored on this machine</li>
 * </ol>
 * Never put tokens in source code or committed files.
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
