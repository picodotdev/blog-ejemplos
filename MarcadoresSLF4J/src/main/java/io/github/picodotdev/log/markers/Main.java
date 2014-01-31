package io.github.picodotdev.log.markers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Main {

	// Loggers
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	// Marcadores
	public static Marker importacion = MarkerFactory.getMarker("IMP");
	public static Marker importacionUtils = MarkerFactory.getMarker("UTL");
	public static Marker persistencia = MarkerFactory.getMarker("PER");
	
	static {
		// Incluir en un marcador las trazas de otro 
		importacionUtils.add(importacion);
	}

	public static void main(String[] args) {
		logger.info("Iniciando...");
		
		logger.info(Main.importacion, "Comenzando importación...");
		new Importador().importar();
		logger.info(Main.importacion, "Importación finalizada");
	}
}