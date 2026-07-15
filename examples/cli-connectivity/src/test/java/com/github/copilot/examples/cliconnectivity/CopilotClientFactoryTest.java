package com.github.copilot.examples.cliconnectivity;

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
    void returnsNullWhenNoTokenValuesAreProvided() {
        assertNull(CopilotClientFactory.resolveToken(null, "", "   "));
    }
}
