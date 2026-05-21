define(['jquery', 'jasmine-html', 'sinon', 'react', 'i18next', 'tareas'], function($, jasmine, sinon, React, i18n, tareas) {
	var resources = {
		dev: {
			translation: {
				"Lista_de_tareas": "Lista de tareas",
			    "COMPLETADAS_tareas_completadas_de_TOTAL": "{{completadas}} tarea completada de {{total}}",
			    "COMPLETADAS_tareas_completadas_de_TOTAL_plural": "{{completadas}} tareas completadas de {{total}}",
			    "Muy_bien_has_completado_todas_las_tareas": "¡Muy bien! has completado todas las tareas",
			    "Limpiar": "Limpiar",
			    "Introduce_una_nueva_tarea": "Introduce una nueva tarea"
			}
		}
	};
	i18n.init({ 
		lng: 'es',
		getAsync: false,
	    interpolationPrefix: '{{',
	    interpolationSuffix: '}}',
	    resStore: resources 
	});
	
	var Utils = {
		render: function(instance) {
			var el = document.createElement('div');
			React.renderComponent(instance, el);
		}			
	}
	
	describe('aplicación lista de tareas', function() {
		var tareaNoCompletada;
		var tareaCompletada;
		
		beforeEach(function() {
			tareaNoCompletada = new tareas.Tarea({ descripcion:'Tarea', completada:false });
			tareaCompletada = new tareas.Tarea({ descripcion:'Tarea', completada:true});
		});
		
		describe('modelo tarea', function() { 
			it('no completado', function() {
				var t = tareaNoCompletada.toJSON();
				expect(t.descripcion).toEqual('Tarea');
				expect(t.completada).toBeFalsy();
			});
			it('completado', function() {
				var t = tareaCompletada.toJSON();
				expect(t.descripcion).toEqual('Tarea');
				expect(t.completada).toBeTruthy();
			});
		});
		describe('modelo tareas', function() {
			var modelos;
			
			beforeEach(function() {
				modelos = [];
				modelos.push(tareaNoCompletada);
				modelos.push(tareaCompletada);
			});

			it('búsqueda completadas', function() {
				var modelo = new tareas.Tareas();
				modelo.reset(modelos);

				expect(1).toEqual(modelo.findCompletadas().length);				
			});
			it('eliminar completadas', function() {
				var modelo = new tareas.Tareas();
				modelo.reset(modelos);

				modelo.removeCompletadas();
				
				expect(1).toEqual(modelo.length);
				expect(0).toEqual(modelo.findCompletadas().length);
			});
		});
		describe('vistas', function() {
			var server;
		
			beforeEach(function() {
				server = sinon.fakeServer.create();
			});
			
			afterEach(function() {
				server.restore();
			});
			
			describe('tareas', function() {
				it('marcar como completada', function() {
					var componente = new tareas.TareaComponent({tarea: tareaNoCompletada});
					Utils.render(componente);
				
					var input = $("input[name='completada']", componente.getDOMNode());
					input.attr('checked', true);
					input.trigger('change');
				
					expect(tareaNoCompletada.get('completada')).toBeTruthy();
					expect(1).toEqual(server.requests.length);
				});
				it('marcar como no completada', function() {
					var componente = new tareas.TareaComponent({tarea: tareaCompletada});
					Utils.render(componente);
				
					var input = $("input[name='completada']", componente.getDOMNode());
					input.attr('checked', false);
					input.trigger('change');
				
					expect(tareaCompletada.get('completada')).toBeFalsy();
					expect(1).toEqual(server.requests.length);
				});
			});
			describe('tareasapp', function() {
				var app;
				
				beforeEach(function() {
					app = new tareas.TareasApp();					
					Utils.render(app);
				});
				
				it('inicializar lista tareas', function() {
					app.resetTareas([tareaNoCompletada, tareaCompletada]);					

					expect(2).toEqual($('li', app.getDOMNode()).length);
					expect('1 tarea completada de 2<br>').toEqual($('div.estado', app.getDOMNode()).html().trim());
				});
				it('añadir una tarea', function() {
					app.addTarea(tareaNoCompletada);
					
					expect(1).toEqual($('li', app.getDOMNode()).length);
				});
				it('nueva tarea', function() {
					var input = $("input[name='nuevaTarea']", app.getDOMNode());
					
					var e = $.Event('keypress');
					e.which = 13;
					
					input.val('Tarea');
					input.trigger(e);
					
					expect(1).toEqual($('li', app.getDOMNode()).length);
					expect('').toEqual(input.val());
					expect(1).toEqual(server.requests.length);
				});
				it('limpiar tareas completadas', function() {
					app.resetTareas([tareaNoCompletada, tareaCompletada]);
					
					var input = $("input[name='limpiar']", app.getDOMNode());									
					input.trigger('click');		

					expect('disabled').toEqual(input.attr('disabled'));
					expect(1).toEqual($('li', app.getDOMNode()).length);
				});
				it('botón limpiar con tareas completadas', function() {
					app.resetTareas([tareaCompletada]);

					var input = $("input[name='limpiar']", app.getDOMNode());

					expect(input.attr('disabled')).toBeUndefined();
				});
				it('botón limpiar con tareas no completadas', function() {
					app.resetTareas([tareaNoCompletada]);
					
					var input = $("input[name='limpiar']", app.getDOMNode());
					
					expect('disabled').toEqual(input.attr('disabled'));
				});
				it('botón limpiar sin tareas', function() {
					var input = $("input[name='limpiar']", app.getDOMNode());
					expect('disabled').toEqual(input.attr('disabled'));
				});
			});
		});
	});
});