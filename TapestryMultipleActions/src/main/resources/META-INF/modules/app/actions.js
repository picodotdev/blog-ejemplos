define("app/actions", ["jquery", "underscore", "app/modals"], function($, _, modals) {
	function ProductActions() {
	    var that = this;

        $("input[type='checkbox'][name='product']").on('change', function() {
            var actions = that.getAttribute(that.getCheckedCheckboxes(), 'data-product-actions');
            actions = _.map(actions, function(actions) {
                return actions.split(',');
            });
            actions = _.intersection.apply(_, actions);
            $("input[data-products-action]").attr('disabled', 'disabled');
            _.each(actions, function(action) {
                $("input[data-products-action='" + action + "']").removeAttr('disabled');
            });
        });

		$("input[type='button'][data-products-action='enable']").on('click', function() {
            var ids = that.getAttribute(that.getCheckedCheckboxes(), 'value');
            $(this).closest('form').find("#ids").val(ids.join(','));
            $('#productsForm')[0].submit();
		});

		$("input[type='button'][data-product-action='disable']").on('click', function() {
		    var id = $(this).closest('form').find("#id").val();
		    new modals.DisableProductsModal().show([id]);
		});

		$("input[type='button'][data-products-action='disable']").on('click', function() {
            var ids = that.getAttribute(that.getCheckedCheckboxes(), 'value');
		    new modals.DisableProductsModal().show(ids);
		});

		$("input[type='checkbox'][name='product']").trigger('change');
	}

	ProductActions.prototype.getCheckedCheckboxes = function() {
        return $("input[type='checkbox'][name='product']:checked");
    };

    ProductActions.prototype.getAttribute = function(elements, attribute) {
        return elements.map(function() {
             return $(this).attr(attribute);
        }).get();
    };

	function init(spec) {
		new ProductActions(spec);
	}

    init();
});