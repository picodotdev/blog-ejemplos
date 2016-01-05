package io.github.picodotdev.plugintapestry.misc;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

	public static ServletContext SERVLET_CONTEXT;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		SERVLET_CONTEXT = sce.getServletContext();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}