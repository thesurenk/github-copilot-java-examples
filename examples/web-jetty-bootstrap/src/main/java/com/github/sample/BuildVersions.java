package com.github.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Build-time and runtime version info shown on the home page.
 */
public final class BuildVersions {

    private static final Properties PROPS = load();

    private BuildVersions() {
    }

    public static String javaVersion() {
        return System.getProperty("java.version", "unknown");
    }

    public static String copilotSdkVersion() {
        return PROPS.getProperty("copilot.sdk.version", "unknown");
    }

    public static String mavenVersion() {
        return PROPS.getProperty("maven.version", "unknown");
    }

    public static String jettyVersion() {
        Package jetty = org.eclipse.jetty.server.Server.class.getPackage();
        if (jetty != null) {
            String impl = jetty.getImplementationVersion();
            if (impl != null && !impl.isBlank()) {
                return impl;
            }
        }
        return PROPS.getProperty("jetty.version", "unknown");
    }

    private static Properties load() {
        Properties props = new Properties();
        try (InputStream in = BuildVersions.class.getResourceAsStream("/versions.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException ignored) {
            // Fall back to "unknown" defaults from getters.
        }
        return props;
    }
}
