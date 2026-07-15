package com.github.sample;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Renders the home page HTML template with live status and optional ask results.
 */
public final class HomePageRenderer {

    private static final String TEMPLATE = loadTemplate();

    private HomePageRenderer() {
    }

    public static String render(StatusSnapshot status, AskResult askResult) {
        String askBlock = "";
        String promptValue = "";
        if (askResult != null) {
            promptValue = askResult.prompt() != null ? askResult.prompt() : "";
            String body = askResult.success()
                    ? askResult.reply()
                    : askResult.error();
            askBlock = """
                    <div class="ask-result __ASK_CLASS__">
                      <h3>__ASK_TITLE__</h3>
                      <p>__ASK_BODY__</p>
                    </div>
                    """.replace("__ASK_CLASS__", askResult.statusClass())
                    .replace("__ASK_TITLE__", askResult.success() ? "Copilot reply" : "Ask failed")
                    .replace("__ASK_BODY__", HtmlEscapes.escape(body));
        }

        return TEMPLATE
                .replace("__STATUS_CLASS__", status.statusClass())
                .replace("__STATUS_LABEL__", HtmlEscapes.escape(status.statusLabel()))
                .replace("__MESSAGE__", HtmlEscapes.escape(status.message()))
                .replace("__AUTH_CLASS__", status.auth().statusClass())
                .replace("__AUTH_LABEL__", HtmlEscapes.escape(status.auth().statusLabel()))
                .replace("__AUTH_SOURCE__", HtmlEscapes.escape(EnvConfig.authSourceLabel()))
                .replace("__AUTH_MESSAGE__", HtmlEscapes.escape(status.auth().message()))
                .replace("__JAVA_VERSION__", HtmlEscapes.escape(status.versions().javaVersion()))
                .replace("__JETTY_VERSION__", HtmlEscapes.escape(status.versions().jettyVersion()))
                .replace("__COPILOT_SDK_VERSION__", HtmlEscapes.escape(status.versions().copilotSdkVersion()))
                .replace("__MAVEN_VERSION__", HtmlEscapes.escape(status.versions().mavenVersion()))
                .replace("__PROMPT_VALUE__", HtmlEscapes.escape(promptValue))
                .replace("__ASK_RESULT_BLOCK__", askBlock);
    }

    private static String loadTemplate() {
        try (InputStream in = HomePageRenderer.class.getResourceAsStream("/home.html")) {
            if (in == null) {
                throw new IllegalStateException("Missing classpath resource /home.html");
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
