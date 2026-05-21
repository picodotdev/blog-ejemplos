package io.github.picodotdev.pattern.nooperation;

public class OperacionCommandFactory {

	public enum Operacion {
		MENSAJE, NO_MENSAJE
	}
	
	public OperacionCommand buildCommand(Operacion operacion) {
		if (operacion == null) {
			return new NoOperacionCommand();
		}
		switch (operacion) {
			case MENSAJE:
				return new MensajeCommand();
			case NO_MENSAJE:
				return new NoOperacionCommand();
			default:
				throw new IllegalArgumentException();
		}
	}
}