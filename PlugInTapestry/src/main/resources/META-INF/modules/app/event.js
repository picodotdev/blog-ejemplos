define("app/event", ["t5/core/ajax", "jquery"], function(ajax, $) {
	function Colores(spec) {
		var _this = this;
		
		this.spec = spec;

		setTimeout(function() {
			_this.getColores();
		}, 2000);
	}

	Colores.prototype.getColores = function() {
		var _this = this;
		
		ajax('getColores', {
			element: $(_this.spec.selector),
			success: function(response) {
				var c = response.json.join();
				$(_this.spec.selector).html(c);
			}
		});
	}

	function init(spec) {
		new Colores(spec);
	}
	
	return {
		init: init
	}
});