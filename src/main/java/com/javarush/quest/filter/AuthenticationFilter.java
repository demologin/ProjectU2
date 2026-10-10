package com.javarush.quest.filter;

import com.javarush.quest.config.constant.Schema.Key;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.entity.User;
import jakarta.annotation.Priority;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@Priority(2)
@WebFilter({Url.QUEST, Url.SCENE, Url.USERS})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User user = Optional.ofNullable(req.getSession(false))
                .map(session -> session.getAttribute(Key.USER))
                .map(User.class::cast)
                .orElse(null);

        if (user != null) {
            req.setAttribute(Key.USER, user);
            chain.doFilter(request, response);
        } else {
            //todo logging
            resp.sendRedirect(req.getContextPath() + Url.LOGIN);
        }
    }
}
