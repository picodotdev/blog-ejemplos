requirejs.config({
     shim: {
          underscore: {
              exports: '_'
          },
          json2: {
               exports: 'JSON'
          },
          backbone: {
               deps: ['jquery', 'underscore', 'json2'],
               exports: 'Backbone'
          },
          bootstrap: {
               "deps": ['jquery']
          },
          mustache: {
          }
     },
     paths: {
          jquery: 'https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min',
          bootstrap: 'https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min',
          react: 'https://cdnjs.cloudflare.com/ajax/libs/react/0.14.7/react.min',
          underscore: 'https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min',
          json2: 'https://cdnjs.cloudflare.com/ajax/libs/json2/20150503/json2.min',
          mustache: 'https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.2.1/mustache.min',
          backbone: 'https://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.3.2/backbone-min'
     }
});