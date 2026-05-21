require(['jquery', 'underscore'], function($, _) {
    $('#products').on('click', 'li button.add', function() {
      var element = $(this).closest('li');
      var amount = parseInt(element.attr('data-amount'));
      amount += 1;
      element.attr('data-amount', amount);
      element.find('span.amount').html(amount);

      updatePrice();
      updateCart();
    });
    $('#products').on('click', 'li button.substract', function() {
      var element = $(this).closest('li');
      var amount = parseInt(element.attr('data-amount'));
      amount -= 1;
      amount = (amount < 0) ? 0 : amount;
      element.attr('data-amount', amount);
      element.find('span.amount').html(amount);

      updatePrice();
      updateCart();
    });

    $('#buy').on('click', function() {
         var json = JSON.stringify(getCart());

         $.ajax({
             url: 'https://localhost:8443/war/rest/purchases',
             type: 'POST',
             dataType: 'text',
             data: json,
             contentType: 'application/json',
             success: function(response) {
             },
             error: function(err) {
             }
        });
    });

    function updateCart() {
         var json = JSON.stringify(getCart());

         $.ajax({
             url: 'https://localhost:8443/war/rest/cart',
             type: 'POST',
             dataType: 'text',
             data: json,
             contentType: 'application/json',
             success: function(response){
             },
             error: function(err){
             }
        });
    }

    function updatePrice() {
        var total = 0;
        $.each($('li', '#products'), function() {
            var price = parseFloat($(this).attr('data-price'));
            var amount = parseInt($(this).attr('data-amount'));
            total += price * amount;
        });
        total = total.toFixed(2);
        var purchasePrice = $('#purchasePrice');
        purchasePrice.attr('data-price', total);
        purchasePrice.html(total + ' €');
    }

    function getCart() {
         var cart = {items: []};
         $.each($('li', '#products'), function() {
              var id = parseInt($(this).attr('data-id'));
              var amount = parseInt($(this).attr('data-amount'));

              if (amount > 0) {
                  cart.items.push({id: id, amount: amount});
              }
         });
         return cart;
    }

    // WebSocket
    var stocks = new WebSocket("wss://localhost:8443/war/stock");
    stocks.onopen = function() {
       stocks.send('ping');
    }

    stocks.onerror = function(evt) {

    }

    stocks.onclose = function() {

    }

    stocks.onmessage = function(message) {
        console.log(message);
        _.each(JSON.parse(message.data), function(it) {
            var element = $('li[data-id="' + it.id + '"]', '#products');
            element.attr('data-stock', it.stock);
            element.find('span.stock').attr('title', it.stock + ' in stock').html(it.stock);
        });
    }

    $(window).on('beforeunload', function(){
        stocks.close();
    });
});