# Web Jetty Bootstrap

A Java 21 web application bootstrap for the GitHub Copilot Java SDK: Maven, embedded Jetty, HTML page, JSON API, and one chat round-trip.

Part of [github-copilot-java-examples](../..).

## What this example shows

- **Java + Maven**: compile, test, and package a runnable fat JAR
- **Embedded Jetty**: serve HTML and JSON from one process
- **Copilot SDK basics**:
  - connectivity check (`start`, `ping`)
  - auth status (`getAuthStatus`)
  - one real chat round-trip (`createSession`, `sendAndWait`)
- **Project layout** beginners can copy and extend

## Prerequisites

- JDK 21+
- Maven 3.9+
- [GitHub Copilot CLI](https://docs.github.com/en/copilot/how-tos/copilot-cli/set-up-copilot-cli/install-copilot-cli) 1.0.55+
- An active GitHub Copilot subscription
- Authentication configured (see [Authentication](#authentication))

## Sample
<img width="750" height="600" alt="github-copilot-web-jetty-example1" src="https://github.com/user-attachments/assets/667517e7-c4b2-44bb-b757-b0971a296fc7" />


## Run

```powershell
mvn -DskipTests package
java -jar target/web-jetty-bootstrap-1.0.0-SNAPSHOT.jar
```

Or use the helper script:

```powershell
.\scripts\run.ps1
```

Open:

- Home page: http://localhost:8080/
- JSON status API: http://localhost:8080/api/status

Optional port override:

```powershell
$env:PORT = "9090"
java -jar target/web-jetty-bootstrap-1.0.0-SNAPSHOT.jar
```

## Authentication

A Copilot **subscription** is tied to your GitHub account. You do not store a subscription key in this repo. You only configure **auth** so the SDK can call Copilot on your behalf.

This example uses [`CopilotClientFactory.java`](src/main/java/com/github/sample/CopilotClientFactory.java) in one place so beginners can see how auth works.

### Option 1: Copilot CLI login (easiest for local dev)

```powershell
copilot
```

Sign in once. Credentials stay on your machine (keychain), not in the project folder. Then run the app with no extra config.

### Option 2: Environment variable (good for scripts and CI)

Set a token in your shell before starting the app:

```powershell
$env:COPILOT_GITHUB_TOKEN = "gho_xxxx"
java -jar target/web-jetty-bootstrap-1.0.0-SNAPSHOT.jar
```

Supported variables (first match wins):

1. `COPILOT_GITHUB_TOKEN` (recommended)
2. `GH_TOKEN`
3. `GITHUB_TOKEN`

When a token env var is set, the factory uses it and skips stored CLI login.

### Local `.env` file (never commit it)

The app loads `.env` automatically at startup from the current working directory.

1. Copy the template:

```powershell
Copy-Item .env.example .env
```

2. Edit `.env` and add your token locally.
3. Run the app from the example root:

```powershell
java -jar target/web-jetty-bootstrap-1.0.0-SNAPSHOT.jar
```

`.env` is in [`.gitignore`](.gitignore) so it will not be pushed to GitHub.

## Project map

| File | Purpose |
|------|---------|
| [`App.java`](src/main/java/com/github/sample/App.java) | Starts embedded Jetty and registers routes |
| [`HomeServlet.java`](src/main/java/com/github/sample/HomeServlet.java) | Serves the home page |
| [`AskServlet.java`](src/main/java/com/github/sample/AskServlet.java) | Handles the Ask Copilot form (`POST /ask`) |
| [`StatusServlet.java`](src/main/java/com/github/sample/StatusServlet.java) | JSON status endpoint (`GET /api/status`) |
| [`CopilotClientFactory.java`](src/main/java/com/github/sample/CopilotClientFactory.java) | Safe auth: env token or CLI login |
| [`CopilotStatusCollector.java`](src/main/java/com/github/sample/CopilotStatusCollector.java) | Connectivity + auth + versions |
| [`CopilotAskService.java`](src/main/java/com/github/sample/CopilotAskService.java) | One prompt/response via Copilot session |
| [`home.html`](src/main/resources/home.html) | HTML template |

## Routes

| Route | Method | Description |
|-------|--------|-------------|
| `/` | GET | Home page with connectivity, auth, versions, and Ask form |
| `/ask` | POST | Sends a prompt to Copilot and re-renders the page |
| `/api/status` | GET | JSON status for scripts and API clients |

## Tests

Unit tests (no CLI required):

```powershell
mvn test -Dtest=StatusJsonTest,DotEnvLoaderTest,CopilotClientFactoryTest
```

Full test suite (includes CLI integration test):

```powershell
mvn test
```

## Next steps

- Try [cli-connectivity](../cli-connectivity/) first if you only need ping + auth
- Add streaming with `session.on(AssistantMessageEvent.class, ...)`
- Explore the [Copilot Java SDK README](https://github.com/github/copilot-sdk/blob/main/java/README.md)
