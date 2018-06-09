define("app/ajax", ["jquery"], function($) {
	function Colores(spec) {
		var _this = this;
		
		this.spec = spec;

		setTimeout(function() {
			_this.getColores();
		}, 2000);
	}

	Colores.prototype.getColores = function() {
		var _this = this;

        $.ajax({
            url: this.spec.link,
			success: function(colores) {
				var c = colores.join();
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