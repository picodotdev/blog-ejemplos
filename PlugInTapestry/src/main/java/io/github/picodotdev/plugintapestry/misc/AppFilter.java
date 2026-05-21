package io.github.picodotdev.plugintapestry.misc;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.*;

import org.apache.logging.log4j.ThreadContext;

public class AppFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            ThreadContext.put("uuid", UUID.randomUUID().toString());
            Globals.HOST.set(request.getServerName());

            chain.doFilter(request, response);
        } finally {
            Globals.HOST.remove();
            ThreadContext.clearAll();
        }
    }

    @Override
    public void destroy() {
    }
}
