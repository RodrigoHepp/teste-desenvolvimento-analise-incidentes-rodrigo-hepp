package br.com.backend.backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestIdFilter implements Filter {

    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String REQUEST_ID_MDC = "requestId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestId = request.getHeader(REQUEST_ID_HEADER);
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }

        MDC.put(REQUEST_ID_MDC, requestId);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
