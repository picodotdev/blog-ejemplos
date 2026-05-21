define("app/saludador", ["jquery"], function($) {
	function Saludador(spec) {
		this.spec = spec;
	}
	
	Saludador.prototype.render = function() {
		$(this.spec.selector).html(this.spec.mensaje);
	}
	
	function init(spec) {
		new Saludador(spec).render();
	}
	
	return {
		init: init
	};	
});