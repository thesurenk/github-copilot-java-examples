package com.github.sample;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DotEnvLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void parsesKeyValuePairsAndIgnoresComments() throws Exception {
        Path env = tempDir.resolve(".env");
        Files.writeString(env, """
                # comment
                COPILOT_GITHUB_TOKEN=abc123
                GH_TOKEN="quoted"
                blank=
                """);

        var values = DotEnvLoader.load(env);

        assertEquals("abc123", values.get("COPILOT_GITHUB_TOKEN"));
        assertEquals("quoted", values.get("GH_TOKEN"));
        assertFalse(values.containsKey("blank"));
    }

    @Test
    void envConfigPrefersProcessEnvironmentOverDotEnv(@TempDir Path tempDir) throws Exception {
        Path env = tempDir.resolve(".env");
        Files.writeString(env, "COPILOT_GITHUB_TOKEN=from-dotenv\n");

        EnvConfig.loadDotEnv(env);

        assertEquals("from-dotenv", EnvConfig.getenv("COPILOT_GITHUB_TOKEN"));
        assertTrue(EnvConfig.hasGitHubToken());
        assertEquals("Token from .env file", EnvConfig.authSourceLabel());
    }
}
