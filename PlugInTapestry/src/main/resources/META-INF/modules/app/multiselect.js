define("app/multiselect", ["jquery"], function($) {

    function Multiselect(spec) {
        this.select = $('#' + spec.clientId);
        var that = this;

        this.select.on('loaded.bs.select', function (event) {
            var tokensSelectors = that.select.data('tokens-selectors');
            if (tokensSelectors == null || tokensSelectors.length == 0) {
                return;
            }
            tokensSelectors = tokensSelectors.split(',');

            var buttons = '';
            $.each(tokensSelectors, function(i, it) {
                buttons += (i % 2 == 0) ? '<div class="btn-group btn-group-sm btn-block">' : '';
                buttons += '<button type="button" data-select-custom-token="' + it + '" class="actions-btn-custom bs-select-custom btn btn-default">' + it.substr(0, 1).toUpperCase() + it.substr(1) + '</button>'
                buttons += (i % 2 == 1 || i + 1 == tokensSelectors.length) ? '</div>' : '';
            });
            $('button[data-id="' + spec.clientId + '"] + div[role="combobox"] div.bs-actionsbox').append(buttons);

            $('button[data-id="' + spec.clientId + '"] + div[role="combobox"] div.bs-actionsbox').on('click', 'button.bs-select-custom', function (event) {
                event.preventDefault();
                event.stopPropagation();

                var token = $(this).attr("data-select-custom-token");
                var values = that.select.find('[data-tokens~=' + token + ']').filter(':not([disabled])').map(function() {
                    return $(this).val();
                }).get();
                that.select.selectpicker('val', values);
            });
        });
    }

	function init(spec) {
		new Multiselect(spec);
	}
	
	return {
		init: init
	}
});