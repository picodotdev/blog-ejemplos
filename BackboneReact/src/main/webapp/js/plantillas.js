define('plantillas', ['mustache', 'text!plantillas/estado.mustache'], function(Mustache, estado) {
	var cache = {};
	
    function get(plantilla) {
    	if (cache[plantilla] == null) {
    		cache[plantilla] = Mustache.compile(plantilla);
    	}
    	return cache[plantilla];
    }
    
    return {
    	estado: function() {
    		return get(estado);
    	}
    }
});