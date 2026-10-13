package com.javarush.quest.filter;

import com.javarush.quest.config.constant.Schema.Jsp;
import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.exception.AppException;
import com.javarush.quest.exception.impl.InvalidCredentialsException;
import jakarta.annotation.Priority;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;

import static com.javarush.quest.exception.ErrorMessage.SERVER_ERROR;

@Slf4j
@Priority(1)
@WebFilter(Url.WILDCARD)
public class ExceptionFilter implements Filter {
    private static final String ERROR_JSP = String.format(Jsp.FORMAT, Jsp.ERROR);
    private static final String LOGIN_JSP = String.format(Jsp.FORMAT, Jsp.LOGIN);

    private record ErrorDetails(int status, String errorMessage, String targetJsp) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);
        } catch (InvalidCredentialsException e) {
            ErrorDetails error = new ErrorDetails(
                    e.getHttpStatus(), e.getErrorMessage(), LOGIN_JSP);

            log.warn("Authentication failed: invalid credentials provided");
            forwardToErrorPage(req, resp, error);
        } catch (AppException e) {
            ErrorDetails error = new ErrorDetails(
                    e.getHttpStatus(), e.getErrorMessage(), ERROR_JSP);

            String message = "Client request failed. Status: {}, Reason: {}";
            log.warn(message, e.getHttpStatus(), e.getMessage());
            forwardToErrorPage(req, resp, error);
        } catch (Exception e) {
            int status = HttpURLConnection.HTTP_INTERNAL_ERROR;
            ErrorDetails error = new ErrorDetails(status, SERVER_ERROR, ERROR_JSP);

            log.error("Critical system error occurred during request execution", e);
            forwardToErrorPage(req, resp, error);
        }
    }

    private void forwardToErrorPage(HttpServletRequest req, HttpServletResponse resp,
                                    ErrorDetails error) throws ServletException, IOException {
        if (!resp.isCommitted()) {
            resp.setStatus(error.status());
            req.setAttribute(Key.ERROR_MESSAGE, error.errorMessage());
            req.getRequestDispatcher(error.targetJsp()).forward(req, resp);
        } else {
            log.error("Response already committed. Cannot forward to target: {}", error.targetJsp());
        }
    }
}
