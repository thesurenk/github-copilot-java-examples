package com.github.copilot.examples.cliconnectivity;

import java.nio.file.Path;
import java.util.Map;

public final class EnvConfig {

    private static volatile Map<String, String> dotEnv = Map.of();
    private static volatile Path loadedFrom;

    private EnvConfig() {
    }

    public static void loadDotEnv(Path path) {
        dotEnv = DotEnvLoader.load(path);
        loadedFrom = dotEnv.isEmpty() ? null : path.toAbsolutePath().normalize();
    }

    public static String getenv(String name) {
        String systemValue = System.getenv(name);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }
        String dotEnvValue = dotEnv.get(name);
        if (dotEnvValue != null && !dotEnvValue.isBlank()) {
            return dotEnvValue.trim();
        }
        return null;
    }

    public static String resolveGitHubToken() {
        return CopilotClientFactory.resolveToken(
                getenv("COPILOT_GITHUB_TOKEN"),
                getenv("GH_TOKEN"),
                getenv("GITHUB_TOKEN"));
    }

    public static boolean hasGitHubToken() {
        return resolveGitHubToken() != null;
    }

    public static String authSourceLabel() {
        if (hasGitHubToken()) {
            if (loadedFrom != null) {
                return "Token from .env file";
            }
            return "Token from environment variable";
        }
        return "Copilot CLI login";
    }
}
