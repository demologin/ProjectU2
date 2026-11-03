package com.javarush.zimina.filter;

import com.javarush.zimina.entity.Role;
import com.javarush.zimina.entity.User;
import com.javarush.zimina.util.Go;
import com.javarush.zimina.util.Key;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@WebFilter({Go.INDEX, Go.HOME, Go.SIGNUP, Go.LOGIN, Go.LOGOUT, Go.PROFILE,
        Go.LIST_QUEST, Go.PLAY_GAME, Go.CREATE_QUEST, Go.EDIT_QUEST})
public class AuthorizationFilter extends HttpFilter {

    private final Map<Role, List<String>> permissions = Map.of(
            Role.GUEST, List.of(
                    Go.INDEX, Go.HOME, Go.LOGIN, Go.SIGNUP,
                    Go.LIST_QUEST, Go.PLAY_GAME),
            Role.USER, List.of(
                    Go.INDEX, Go.HOME, Go.LOGIN, Go.SIGNUP,
                    Go.LIST_QUEST, Go.PLAY_GAME,
                    Go.LOGOUT, Go.PROFILE, Go.CREATE_QUEST, Go.EDIT_QUEST),
            Role.ADMIN, List.of(
                    Go.INDEX, Go.HOME, Go.LOGIN, Go.SIGNUP,
                    Go.LIST_QUEST, Go.PLAY_GAME,
                    Go.LOGOUT, Go.PROFILE, Go.CREATE_QUEST, Go.EDIT_QUEST)
    );

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String path = req.getServletPath();
        User user = (User) req.getSession().getAttribute(Key.USER);
        Role role = (user == null) ? Role.GUEST : user.getRole();

        if (permissions.get(role).contains(path)) {
            chain.doFilter(req, res);
        } else {
            log.warn("Access denied: {} for role {}", path, role);
            res.sendRedirect(Go.LOGIN);
        }
    }
}