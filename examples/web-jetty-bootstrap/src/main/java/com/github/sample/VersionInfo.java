package com.github.sample;

/**
 * Runtime and build versions shown on the home page and status API.
 */
public record VersionInfo(String javaVersion, String jettyVersion, String copilotSdkVersion, String mavenVersion) {

    public static VersionInfo current() {
        return new VersionInfo(
                BuildVersions.javaVersion(),
                BuildVersions.jettyVersion(),
                BuildVersions.copilotSdkVersion(),
                BuildVersions.mavenVersion());
    }
}
