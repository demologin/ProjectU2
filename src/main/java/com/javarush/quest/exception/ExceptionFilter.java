package com.javarush.quest.exception;

import com.javarush.quest.config.constant.Schema.Jsp;
import com.javarush.quest.config.constant.Schema.Url;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(Url.WILDCARD)
public class ExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            chain.doFilter(request, response);
        } catch (EntityNotFoundException e) {
            extracted(request, response, e, resp);
        } catch (Throwable e) {
            //todo log somewhere e.printStackTrace()
            resp.setStatus(500);
            request.getRequestDispatcher(getPath()).forward(request, response);
        }
    }

    private static void extracted(ServletRequest request, ServletResponse response, EntityNotFoundException e, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(e.getHttpStatus());
        request.getRequestDispatcher(getPath()).forward(request, response);
    }

    private static String getPath() {
        return Jsp.ROOT + Jsp.ERROR + Jsp.JSP;
    }
}
