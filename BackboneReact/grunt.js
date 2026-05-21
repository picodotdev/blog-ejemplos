module.exports = function(grunt) {
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),
		jasmine: {
			requirejs: {
				options: {
					specs: 'src/**/specs/main-specs.js',
					template: require('grunt-template-jasmine-requirejs'),
					templateOptions: {
						requireConfigFile : 'src/main/webapp/js/specs/requirejs-config.js',
						requireConfig: {
							baseUrl: './src/main/webapp/js'
						}
					}
				}
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-jasmine');
	
	grunt.registerTask('test', ['connect', 'jasmine:requirejs']);
};