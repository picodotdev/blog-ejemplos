package io.github.picodotdev.plugintapestry.geb

import geb.Page
import geb.spock.GebSpec

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.IntegrationTest

import io.github.picodotdev.plugintapestry.spring.AppConfiguration

// Definición de la página índice 
class IndexPage extends Page {
    // Localización
    static url = 'http://localhost:8080/'
    // Determinar que se cargó una página 
    static at = { title.startsWith('PlugIn') }
    // Definición de los elementos de la página
    static content = {
        meta { $('meta[pagina]') }
    }
}

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = AppConfiguration.class)
@WebAppConfiguration
@IntegrationTest
class IndexSpec extends GebSpec {
    def 'go to index'() {
        when:
        to IndexPage

        then:
		meta.@pagina == 'Index'
   }
}