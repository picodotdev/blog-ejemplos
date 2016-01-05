define("app/listSelect", ["jquery"], function($) {
	function ListSelect(spec) {
		var _this = this;
		
		this.spec = spec;
		
		$("#" + this.spec.all).on("change", function() {
			_this.onChangeAll();
		});
		$("#" + this.spec.checkboxs + " input[type='checkbox']").not("#" + this.spec.all).on("change", function() {
			_this.onChangeCheckbox();
		});
	}
	
	ListSelect.prototype.onChangeAll = function() {
		var checked = $("#" + this.spec.all).is(":checked");
		$("#" + this.spec.checkboxs + " input[type='checkbox']").not("#" + this.spec.all).prop("checked", checked);
	}
	
	ListSelect.prototype.onChangeCheckbox = function() {
		var checked = this.isAllChecked();
		$("#" + this.spec.all).prop("checked", checked);
	}
	
	ListSelect.prototype.isAllChecked = function() {
		return $("#" + this.spec.checkboxs + " input[type='checkbox']").not("#" + this.spec.all).length == $("#" + this.spec.checkboxs + " input[type='checkbox']:checked").not("#" + this.spec.all).length;
	}

	function init(spec) {
		new ListSelect(spec);
	}
	
	return {
		init: init
	}
});