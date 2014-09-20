package io.github.picodotdev.blogbitix.config;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class AppConfiguracion implements Configuracion {
	
	private static Map<String, Object> config;

	static {
		config = new HashMap<>();
		config.put("propiedad", 5);
	}

	public Map get() {
		return config;
	}
}
