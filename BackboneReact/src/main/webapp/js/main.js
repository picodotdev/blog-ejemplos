define(['react', 'tareas'], function(React, tareas) {
	var tareasApp = React.renderComponent(tareas.TareasApp(null), $('#tareas')[0]);

	// Cargar los datos iniciales de la lista de tareas
	// Usar los datos precargados en la página, para evitar una petición
	// al servidor, los datos se incluyen en la página html de la aplicación.
	//tareasApp.resetTareas(tareas);
	
	// Aunque en la documentación de backbone recomiendan precargar los datos en la
	// página, esto impide cachearla, dependiendo
	// de la página tal vez sea mejor cachear la página y pedir los datos 
	// en una petición AJAX.
	tareasApp.fetch();
});