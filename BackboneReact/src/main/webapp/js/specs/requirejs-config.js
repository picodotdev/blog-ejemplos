requirejs.config({
	baseUrl: './',
	shim : {
		'underscore': {
			exports: '_'
		},
		'backbone': {
			deps: ['underscore'],
			exports: 'Backbone'
		},
        'json2': {
            exports: 'JSON'
        },
        'sinon': {
            exports: 'sinon'
        }
	},
	locale: 'es'
});