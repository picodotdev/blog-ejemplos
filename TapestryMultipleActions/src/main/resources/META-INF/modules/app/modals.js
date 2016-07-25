define("app/modals", ["jquery", "bootstrap/modal"], function($, modal) {
    function DisableProductsModal() {
    }

	DisableProductsModal.prototype.show = function(ids) {
        var that = this;

	    $.get({
	        url: $('#disableProductsModal').attr('data-url'),
	        data: {
	            'ids': ids.join(',')
	        },
			success: function(html) {
			    that.reset();
				$('#disableProductsModal').html(html.content);
			    $('#disableProductsModal').modal('show');
			}
	    });
	};

	DisableProductsModal.prototype.reset = function() {
        $('#errors', '#disableProductsModal').remove();
	};

	return {
		DisableProductsModal: DisableProductsModal
	};
});