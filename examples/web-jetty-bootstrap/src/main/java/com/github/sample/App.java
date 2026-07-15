package com.github.sample;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;

import java.nio.file.Path;

/**
 * Starts an embedded Jetty server for the Copilot connectivity sample.
 */
public final class App {

    private static final int DEFAULT_PORT = 8080;

    private App() {
    }

    public static void main(String[] args) throws Exception {
        EnvConfig.loadDotEnv(Path.of(".env"));

        int port = resolvePort(args);
        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler("/");
        context.addServlet(new ServletHolder(HomeServlet.class), "/");
        context.addServlet(new ServletHolder(AskServlet.class), "/ask");
        context.addServlet(new ServletHolder(StatusServlet.class), "/api/status");
        server.setHandler(context);

        server.start();
        System.out.println("Home page: http://localhost:" + port + "/");
        System.out.println("Status API: http://localhost:" + port + "/api/status");
        server.join();
    }

    private static int resolvePort(String[] args) {
        if (args.length > 0) {
            return Integer.parseInt(args[0]);
        }
        String envPort = System.getenv("PORT");
        if (envPort != null && !envPort.isBlank()) {
            return Integer.parseInt(envPort);
        }
        return DEFAULT_PORT;
    }
}
