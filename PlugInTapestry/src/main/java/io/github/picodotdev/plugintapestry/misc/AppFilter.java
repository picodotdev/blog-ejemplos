package io.github.picodotdev.plugintapestry.misc;

import javax.servlet.*;
import java.io.IOException;

public class AppFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Globals.HOST.set(request.getServerName());

            chain.doFilter(request, response);
        } finally {
            Globals.HOST.remove();
        }
    }

    @Override
    public void destroy() {
    }
}
