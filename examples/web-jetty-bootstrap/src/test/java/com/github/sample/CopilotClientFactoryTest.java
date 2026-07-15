package com.github.sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CopilotClientFactoryTest {

    @Test
    void prefersCopilotGitHubTokenOverOtherValues() {
        assertEquals(
                "copilot-token",
                CopilotClientFactory.resolveToken("copilot-token", "gh-token", "github-token"));
    }

    @Test
    void fallsBackToGhTokenThenGithubToken() {
        assertEquals("gh-token", CopilotClientFactory.resolveToken(null, "gh-token", "github-token"));
        assertEquals("github-token", CopilotClientFactory.resolveToken(null, null, "github-token"));
    }

    @Test
    void returnsNullWhenNoTokenValuesAreProvided() {
        assertNull(CopilotClientFactory.resolveToken(null, "", "   "));
    }
}
