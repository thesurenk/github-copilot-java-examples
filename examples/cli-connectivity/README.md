# CLI Connectivity

Minimal example: verify GitHub Copilot CLI connectivity and authentication with the Java SDK — no web server.

Part of [github-copilot-java-examples](../..).

## What this example shows

- Start the Copilot SDK client
- Ping the CLI (`start`, `ping`)
- Check auth status (`getAuthStatus`)
- Safe token loading from `.env` or environment variables

## Run

```powershell
mvn -DskipTests package
java -jar target/cli-connectivity-1.0.0-SNAPSHOT.jar
```

Expected output (when configured correctly):

```text
Auth source: Token from .env file
Connectivity: PASS - Connected - protocol version: 3, message: pong: connectivity-check
Authentication: SIGNED IN - Signed in as your-username
```

## Authentication

Copy `.env.example` to `.env` and set `COPILOT_GITHUB_TOKEN`, or run `copilot` to sign in via CLI.

See [web-jetty-bootstrap](../web-jetty-bootstrap/README.md#authentication) for full auth options.

## Next step

Try [web-jetty-bootstrap](../web-jetty-bootstrap/) to add a Jetty web UI on top of the same SDK patterns.
