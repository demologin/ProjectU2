package com.javarush.sharifullina.filter;

import com.javarush.sharifullina.utils.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class ErrorCleanerFilter extends HttpFilter {

        @Override
        protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
                throws IOException, ServletException {

            String uri = req.getRequestURI();
            // Игнорируем запросы к картинкам, стилям и скриптам, чтобы они не съедали ошибку
            boolean isStaticResource = uri.endsWith(".css") || uri.endsWith(".js")
                    || uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".ico");

            if (!isStaticResource && "GET".equalsIgnoreCase(req.getMethod())) {
                HttpSession session = req.getSession(false);
                if (session != null) {
                    Object error = session.getAttribute(Constant.ERROR_MESSAGE);
                    if (error != null) {
                        // Перекладываем в request (он проживет только этот один показ страницы)
                        req.setAttribute(Constant.ERROR_MESSAGE, error);
                        // Сразу удаляем из сессии, чтобы при F5 ошибка больше не появлялась
                        session.removeAttribute(Constant.ERROR_MESSAGE);
                    }
                }
            }

            chain.doFilter(req, res);
        }
}
