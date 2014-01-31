package io.github.picodotdev.log.markers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static void save() {
		logger.info("Comenzando persistencia (utils)...");
		
		// ...
		
		logger.info(Main.persistencia, "Persistido algo (utils)...");
	}
	
	public static void importar() {
		logger.info("Comenzando importación (utils)...");
		
		// ...
		
		logger.info(Main.importacionUtils, "Importado algo (utils)...");
	}	
}
