package io.github.picodotdev.todo.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/tareas")
public interface TareasResource {

	@Path("/tarea")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Tarea createTarea(Tarea tarea);

	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Tarea> readTareas();

	@Path("/tarea/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Tarea updateTarea(Tarea tarea);

	@Path("/tarea/{id}")
	@DELETE
	public void deleteTarea(@PathParam("id") Long id);
}