package io.github.picodotdev.pattern.nooperation;

public class MensajeCommand implements OperacionCommand {

	@Override
	public void operacion() {
		System.out.println("Hola mundo!");
	}
}