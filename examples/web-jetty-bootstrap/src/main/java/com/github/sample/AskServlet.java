package com.github.sample;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Handles Ask Copilot form submissions and re-renders the home page with the reply.
 */
public final class AskServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String prompt = req.getParameter("prompt");
        AskResult askResult = CopilotAskService.ask(prompt);
        HomeServlet.writePage(resp, CopilotStatusCollector.collect(), askResult);
    }
}
