package ua.martishyn.app.controller.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {
    private static final Logger log = LogManager.getLogger(EncodingFilter.class);
    private static final String UTF_ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Encoding filter loaded");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(UTF_ENCODING);
        response.setCharacterEncoding(UTF_ENCODING);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        log.info("Encoding filter destroyed");
    }
}
