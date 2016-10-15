define("app/index", ["jquery"], function($) {
	function init(spec) {
	    $('.selectpicker').selectpicker();
	}

	return {
		init: init
	}
});

