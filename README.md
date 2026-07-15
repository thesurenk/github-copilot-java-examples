# GitHub Copilot Java SDK Examples

Focused, runnable examples for the [GitHub Copilot Java SDK](https://github.com/github/copilot-sdk/tree/main/java).

Each example is a standalone Maven project you can copy and run.

## Examples

| Example | Description | Run |
|---------|-------------|-----|
| [web-jetty-bootstrap](examples/web-jetty-bootstrap/) | Jetty web app: connectivity, auth, chat form, JSON API | `mvn -DskipTests package && java -jar target/web-jetty-bootstrap-1.0.0-SNAPSHOT.jar` |
| [cli-connectivity](examples/cli-connectivity/) | Minimal CLI ping + auth check (no web server) | `mvn -DskipTests package && java -jar target/cli-connectivity-1.0.0-SNAPSHOT.jar` |

## Prerequisites (all examples)

- JDK 21+
- Maven 3.9+
- [GitHub Copilot CLI](https://docs.github.com/en/copilot/how-tos/copilot-cli/set-up-copilot-cli/install-copilot-cli) 1.0.55+
- Active GitHub Copilot subscription
- Authentication: `copilot` login or `COPILOT_GITHUB_TOKEN` in `.env`

## Recommended learning path

1. **cli-connectivity** — verify CLI + SDK wiring
2. **web-jetty-bootstrap** — build a small web UI on top

## Naming convention

Examples use `{domain}-{focus}` folder names:

- `web-jetty-bootstrap` — web + Jetty starter
- `cli-connectivity` — CLI connectivity only

Planned examples: `console-chat`, `streaming-events`, `custom-tools`, `session-resume`.

## SDK documentation

- [Copilot Java SDK README](https://github.com/github/copilot-sdk/blob/main/java/README.md)
- [Javadoc](https://javadoc.io/doc/com.github/copilot-sdk-java/latest/index.html)
