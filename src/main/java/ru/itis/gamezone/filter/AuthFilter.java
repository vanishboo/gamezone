package ru.itis.gamezone.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getServletPath();
        req.setAttribute("contextPath", req.getContextPath());
        if (path.startsWith("/login")
                || path.startsWith("/register")
                || path.startsWith("/logout")
                || path.startsWith("/css")
                || path.startsWith("/js")
                || path.equals("/")
                || path.isEmpty()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (req.getSession().getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        filterChain.doFilter(req, resp);
    }
}
