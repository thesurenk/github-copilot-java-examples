$ErrorActionPreference = "Stop"
Set-Location (Join-Path (Split-Path -Parent $MyInvocation.MyCommand.Path) "..")

mvn -DskipTests package
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

java -jar target/web-jetty-bootstrap-1.0.0-SNAPSHOT.jar @args
