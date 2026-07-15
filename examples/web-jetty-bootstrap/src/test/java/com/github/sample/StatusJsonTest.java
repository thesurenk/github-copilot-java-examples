package com.github.sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatusJsonTest {

    @Test
    void serializesConnectedStatusWithAuthAndVersions() {
        StatusSnapshot status = new StatusSnapshot(
                true,
                "Connected - protocol version: 3",
                new AuthStatus(true, true, "oauth", "octocat", "Signed in as octocat"),
                new VersionInfo("21.0.8", "12.0.16", "1.0.7-preview.1", "3.9.11"));

        String json = StatusJson.toJson(status);

        assertTrue(json.contains("\"connected\":true"));
        assertTrue(json.contains("\"message\":\"Connected - protocol version: 3\""));
        assertTrue(json.contains("\"authenticated\":true"));
        assertTrue(json.contains("\"login\":\"octocat\""));
        assertTrue(json.contains("\"java\":\"21.0.8\""));
        assertTrue(json.contains("\"jetty\":\"12.0.16\""));
        assertTrue(json.contains("\"copilotSdk\":\"1.0.7-preview.1\""));
        assertTrue(json.contains("\"maven\":\"3.9.11\""));
    }

    @Test
    void escapesSpecialCharactersInJson() {
        String escaped = StatusJson.escape("line1\nline2\t\"quoted\"\\slash");
        assertEquals("line1\\nline2\\t\\\"quoted\\\"\\\\slash", escaped);
    }

    @Test
    void serializesDisconnectedStatus() {
        StatusSnapshot status = new StatusSnapshot(
                false,
                "CLI process exited unexpectedly",
                AuthStatus.unknown("Auth check skipped"),
                VersionInfo.current());

        String json = StatusJson.toJson(status);

        assertTrue(json.contains("\"connected\":false"));
        assertFalse(json.contains("\"authenticated\":true"));
    }
}
