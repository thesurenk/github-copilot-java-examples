package com.github.sample;

import com.github.copilot.CopilotClient;
import com.github.copilot.ConnectionState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Sample connectivity check against the GitHub Copilot CLI via the Java SDK.
 * <p>
 * Requires GitHub Copilot CLI 1.0.55+ installed and available on PATH
 * (or configured via CopilotClientOptions).
 */
class CopilotConnectivityTest {

    @Test
    void pingSucceedsWhenCliIsReachable() throws Exception {
        try (var client = CopilotClientFactory.create()) {
            client.start().get();

            assertEquals(ConnectionState.CONNECTED, client.getState(),
                    "Client should be connected after start()");

            var pong = client.ping("connectivity-check").get();

            assertNotNull(pong, "Ping response must not be null");
            assertNotNull(pong.protocolVersion(), "Protocol version must not be null");
            assertTrue(pong.protocolVersion() > 0,
                    "Protocol version should be a positive number, got: " + pong.protocolVersion());

            System.out.println("Connected — protocol version: " + pong.protocolVersion()
                    + ", message: " + pong.message());
        }
    }
}
