package io.github.picodotdev.log.markers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Importador {

	private static final Logger logger = LoggerFactory.getLogger(Importador.class);
	
	public Importador() {		
	}
	
	public void importar() {
		logger.info(Main.importacion, "Realizando importación...");

		Utils.importar();
		
		logger.info(Main.persistencia, "Persistiendo algo...");
		
		Utils.save();
	}
}