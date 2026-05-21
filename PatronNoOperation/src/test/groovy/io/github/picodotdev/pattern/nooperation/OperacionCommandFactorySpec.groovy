package io.github.picodotdev.pattern.nooperation

import io.github.picodotdev.pattern.nooperation.OperacionCommand
import io.github.picodotdev.pattern.nooperation.OperacionCommandFactory
import io.github.picodotdev.pattern.nooperation.OperacionCommandFactory.Operacion

import spock.lang.Specification

public class OperacionCommandFactorySpec extends Specification {

	private OperacionCommandFactory factory = null
	
	void setup() {
		factory = new OperacionCommandFactory()
	}
	
	void test1() {
		setup:
		OperacionCommand operacion = factory.buildCommand(Operacion.MENSAJE)
	
		when:
		operacion.operacion()
		
		then:
		1 == 1
	}
	
	void test2() {
		setup:
		OperacionCommand operacion = factory.buildCommand(Operacion.NO_MENSAJE)
	
		when:
		operacion.operacion()
		
		then:
		1 == 1
	}
	
	void test3() {
		setup:
		OperacionCommand operacion = factory.buildCommand(null)
	
		when:
		operacion.operacion()
		
		then:
		1 == 1
	}
}