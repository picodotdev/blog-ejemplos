package io.github.picodotdev.todo.rest;

import java.util.Collection;

import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class TareasResourceClient implements TareasResource {

	private TareasResource client;

	public TareasResourceClient() {
		// Obtener el cliente a partir de la interfaz y de donde está localizado
		client = ProxyFactory.create(TareasResource.class, "http://localhost:8080/backbone-rest/rest");
	}

	@Override
	public Tarea createTarea(Tarea tarea) {
		return client.createTarea(tarea);
	}

	@Override
	public Collection<Tarea> readTareas() {
		return client.readTareas();
	}

	@Override
	public Tarea updateTarea(Tarea tarea) {
		return client.updateTarea(tarea);
	}

	@Override
	public void deleteTarea(Long id) {
		client.deleteTarea(id);
	}

	public static void main(String[] args) throws Exception {
		// Inicializacion a realizar una vez por máquina virtual
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		
		// Crear el cliente del servicio REST
		TareasResourceClient client = new TareasResourceClient();
		
		// Crear una tarea
		client.createTarea(new Tarea("Mi primera tarea", false));
			
		// Obtener la lista de tareas
		Collection<Tarea> tareas = client.readTareas();
		System.out.println(tareas);
		
		// Modificar una tarea
		Tarea tarea = tareas.iterator().next();
		tarea.setCompletada(true);
		client.updateTarea(tarea);
		
		// Eliminar una tarea
		client.deleteTarea(tarea.getId());
	}
}