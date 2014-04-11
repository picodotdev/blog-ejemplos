package io.github.picodotdev.todo.rest;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Application extends javax.ws.rs.core.Application {
	
	private Set<Object> singletons;
	 
	public Application(Collection<Object> singletons) {
		this.singletons = new HashSet<Object>();
		this.singletons.addAll(singletons);
	}
 
	@Override
	public Set<Class<?>> getClasses() {
		return Collections.EMPTY_SET;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}