({
    baseUrl: 'src/main/webapp/js',
    name: 'main',
    out: 'src/main/webapp/js/main.min.js',
    shim: {
        'underscore': {
            exports: '_'
        },
        'json2': {
            exports: 'JSON'
        },
        'backbone': {
            deps: ['jquery', 'underscore', 'json2'],
            exports: 'Backbone'
        }
    },
})
