package io.github.picodotdev.todo.rest;

import java.util.Collection;
import java.util.TreeMap;

public class TareasResourceImpl implements TareasResource {

	private TreeMap<Long, Tarea> tareas;

	public TareasResourceImpl() {
		tareas = new TreeMap<Long, Tarea>();
	}

	@Override
	public Tarea createTarea(Tarea tarea) {
		Long id = 1l;
		if (!tareas.isEmpty()) {
			id = tareas.lastKey() + 1;
		}
		tarea.setId(id);
		tareas.put(id, tarea);
		return tarea;
	}

	@Override
	public Collection<Tarea> readTareas() {
		return tareas.values();
	}

	@Override
	public Tarea updateTarea(Tarea tarea) {
		tareas.put(tarea.getId(), tarea);
		return tarea;
	}

	@Override
	public void deleteTarea(Long id) {
		tareas.remove(id);
	}
}