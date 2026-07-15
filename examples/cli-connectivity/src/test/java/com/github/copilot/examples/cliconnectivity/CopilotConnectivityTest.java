package com.github.copilot.examples.cliconnectivity;

import com.github.copilot.CopilotClient;
import com.github.copilot.ConnectionState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test — requires Copilot CLI 1.0.55+ on PATH and authentication.
 */
class CopilotConnectivityTest {

    @Test
    void pingSucceedsWhenCliIsReachable() throws Exception {
        try (var client = CopilotClientFactory.create()) {
            client.start().get();

            assertEquals(ConnectionState.CONNECTED, client.getState());

            var pong = client.ping("connectivity-check").get();
            assertNotNull(pong.protocolVersion());
            assertTrue(pong.protocolVersion() > 0);
        }
    }
}
