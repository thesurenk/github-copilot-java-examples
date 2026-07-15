package com.github.sample;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * Serves the home page with connectivity, auth, and an Ask Copilot form.
 */
public final class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        writePage(resp, CopilotStatusCollector.collect(), null);
    }

    static void writePage(HttpServletResponse resp, StatusSnapshot status, AskResult askResult)
            throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(HomePageRenderer.render(status, askResult));
        }
    }
}
