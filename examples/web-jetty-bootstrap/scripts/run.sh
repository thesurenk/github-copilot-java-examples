#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

mvn -DskipTests package
exec java -jar target/web-jetty-bootstrap-1.0.0-SNAPSHOT.jar "$@"
