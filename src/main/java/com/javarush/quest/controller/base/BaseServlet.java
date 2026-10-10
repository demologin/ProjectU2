package com.javarush.quest.controller.base;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.annotation.ServletMetadata;
import com.javarush.quest.config.constant.Schema.Jsp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import static com.javarush.quest.exception.ErrorMessage.ANNOTATION_REQUIRED;

public abstract class BaseServlet extends HttpServlet implements ServletMetadata {
    private final String defaultJsp;

    protected BaseServlet() {
        Servlet annotation = getClass().getAnnotation(Servlet.class);

        if (annotation == null) {
            throw new IllegalStateException(ANNOTATION_REQUIRED + getClass().getName());
        }

        if (annotation.defaultJsp().isBlank()) {
            String urlMapping = getUrlPatterns(annotation)[0];
            defaultJsp = setDefaultJsp(urlMapping);
        } else {
            defaultJsp = annotation.defaultJsp();
        }
    }

    private String setDefaultJsp(String urlMapping) {
        return Optional.of(urlMapping)
                .map(m -> m.endsWith("/*") ? m.substring(0, m.length() - 2) : m)
                .map(m -> m.startsWith("/") ? m.substring(1) : m)
                .map(m -> m.contains("/") ? m.substring(m.lastIndexOf("/") + 1) : m)
                .filter(m -> !m.isBlank())
                .orElse(Jsp.INDEX);
    }

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp, handleGet(req));
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp, handlePost(req));
    }

    protected Response handleGet(HttpServletRequest request) {
        return Response.JSP;
    }

    protected Response handlePost(HttpServletRequest request) {
        return Response.JSP;
    }

    private void process(HttpServletRequest req, HttpServletResponse resp,
                         @NonNull Response action) throws ServletException, IOException {
        switch (action) {
            case Response.JSP() -> {
                String jsp = buildPathToJsp(defaultJsp);
                req.getRequestDispatcher(jsp).forward(req, resp);
            }
            case Response.Forward(String target, boolean isJsp) -> {
                String path = isJsp ? buildPathToJsp(target) : target;
                req.getRequestDispatcher(path).forward(req, resp);
            }
            case Response.Redirect(String target, Map<String, String> queryParams) -> {
                String location = buildRedirectUrl(target, queryParams);
                resp.sendRedirect(req.getContextPath() + location);
            }
        }
    }

    private String buildPathToJsp(String jsp) {
        return String.format(Jsp.JSP_FORMAT, jsp);
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
