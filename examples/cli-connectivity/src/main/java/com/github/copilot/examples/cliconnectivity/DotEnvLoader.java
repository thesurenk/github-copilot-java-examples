package com.github.copilot.examples.cliconnectivity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DotEnvLoader {

    private DotEnvLoader() {
    }

    public static Map<String, String> load(Path path) {
        if (!Files.isRegularFile(path)) {
            return Map.of();
        }

        Map<String, String> values = new HashMap<>();
        try {
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                parseLine(line).ifPresent(entry -> values.put(entry.key(), entry.value()));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read " + path.toAbsolutePath(), e);
        }
        return Collections.unmodifiableMap(values);
    }

    static java.util.Optional<Entry> parseLine(String line) {
        if (line == null) {
            return java.util.Optional.empty();
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty() || trimmed.startsWith("#")) {
            return java.util.Optional.empty();
        }

        int equalsIndex = trimmed.indexOf('=');
        if (equalsIndex <= 0) {
            return java.util.Optional.empty();
        }

        String key = trimmed.substring(0, equalsIndex).trim();
        String value = trimmed.substring(equalsIndex + 1).trim();
        if ((value.startsWith("\"") && value.endsWith("\""))
                || (value.startsWith("'") && value.endsWith("'"))) {
            value = value.substring(1, value.length() - 1);
        }
        if (key.isEmpty() || value.isBlank()) {
            return java.util.Optional.empty();
        }
        return java.util.Optional.of(new Entry(key, value));
    }

    record Entry(String key, String value) {
    }
}
