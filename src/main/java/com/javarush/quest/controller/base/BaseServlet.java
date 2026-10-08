package com.javarush.quest.controller.base;

import com.javarush.quest.config.annotation.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

public abstract class BaseServlet extends HttpServlet {
    private final String defaultJspName;

    protected BaseServlet() {
        Servlet meta = getClass().getAnnotation(Servlet.class);
        if (meta == null) {
            throw new IllegalStateException("No Servlet annotation found");
        }
        defaultJspName = setDefaultJspName(meta);
    }

    private String setDefaultJspName(Servlet meta) {
        if (!meta.jsp().isBlank()) return meta.jsp();

        String urlMapping = meta.value()[0];
        return Optional.of(urlMapping)
                .map(m -> m.endsWith("/*") ? m.substring(0, m.length() - 2) : m)
                .map(m -> m.startsWith("/") ? m.substring(1) : m)
                .map(m -> m.contains("/") ? m.substring(m.lastIndexOf("/") + 1) : m)
                .filter(m -> !m.isBlank())
                .orElse("index"); //todo constant?
    }

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
        process(handleGet(req), req, resp);
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
        process(handlePost(req), req, resp);
    }

    protected Response handleGet(HttpServletRequest req) {
        return Response.DEFAULT;
    }

    protected Response handlePost(HttpServletRequest req) {
        throw new UnsupportedOperationException("Unsupported operation"); //todo or default?
    }

    private void process(Response responseAction, HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        if (responseAction == null) return;

        switch (responseAction) {
            case Response.Default() -> {
                String jsp = getJsp(defaultJspName);
                req.getRequestDispatcher(jsp).forward(req, resp);
            }
            case Response.Forward(String target, boolean isJsp) -> {
                String path = isJsp ? getJsp(target) : target;
                req.getRequestDispatcher(path).forward(req, resp);
            }
            case Response.Redirect(String target, Map<String, String> queryParams) -> {
                String location = buildRedirectUrl(target, queryParams);
                resp.sendRedirect(req.getContextPath() + location);
            }
        }
    }

    private String getJsp(String jsp) {
        return String.format("/WEB-INF/%s.jsp", jsp);
    }

    private String buildRedirectUrl(String target, Map<String, String> queryParams) {
        String string = target.startsWith("/") ? target : "/" + target;
        if (queryParams.isEmpty()) return string;

        StringJoiner joiner = new StringJoiner("&", "?", "");
        queryParams.forEach((key, value) -> joiner.add(key + "="
                + URLEncoder.encode(value, StandardCharsets.UTF_8)));
        return string + joiner;
    }
}
