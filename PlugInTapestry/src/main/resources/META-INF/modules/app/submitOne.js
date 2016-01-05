define("app/submitOne", ["jquery"], function($) {

	var SubmitOne = function(spec) {
		this.spec = spec;
		this.timeout = null;
		
		var element = $('#' + this.spec.elementId);
		
		this.blocked = false;

		var _this = this;
		$(document).ajaxStart(function() {
			_this.onAjaxStart();				
		});
		$(document).ajaxStop(function() {
			_this.onAjaxStop();
		});

		if (element.is('form')) {
			element.on('submit', function(event) {
				return _this.onSubmit(event);
			});
		} else {			
			element.on('click', function(event) {
				return _this.onSubmit(event);
			});			
		}
	}

	SubmitOne.prototype.onSubmit = function(event) {
		if (this.isBlocked()) {
			event.preventDefault();
			return false;
		} else {
			this.block();
			return true;
		}
	}
	
	SubmitOne.prototype.onAjaxStart = function() {
		this.block();
	}
	
	SubmitOne.prototype.onAjaxStop = function() {
		this.unblock();		
	}
	
	SubmitOne.prototype.isBlocked = function() {
		return this.blocked;
	}
	
	SubmitOne.prototype.block = function() {
		this.blocked = true;
	}
	
	SubmitOne.prototype.unblock = function() {
		this.blocked = false;
	}
	
	function init(spec) {
		new SubmitOne(spec);
	}

	return {
		init: init
	};
});