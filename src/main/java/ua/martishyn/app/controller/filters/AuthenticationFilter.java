package ua.martishyn.app.controller.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);
    private static final List<String> commonPages = Arrays.asList("/login-page.command",
            "/login.command", "/register.command", "/register-page.command", "/about-us.command",
            "/index.command", "/view", "/search-tickets.command", "/show-route.command");

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("Authorization filter loaded");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String page = req.getRequestURI().substring(req.getContextPath().length());

        log.info("Authentication check in progress");
        long count = commonPages.stream()
                .filter(page::startsWith)
                .count();
        if (count != 0) {
            chain.doFilter(request, response);
            return;
        }

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            log.info("Authentication check in progress. User not found. Redirecting to login page");
            resp.sendRedirect(req.getContextPath() + "/login-page.command");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("Authorization filter destroyed");
    }
}
