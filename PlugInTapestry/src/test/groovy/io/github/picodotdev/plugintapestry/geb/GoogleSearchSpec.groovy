package io.github.picodotdev.plugintapestry.geb

import geb.Page
import geb.spock.GebSpec
 
class GoogleHomePage extends Page {
    static url = 'https://www.google.es/'
    static at = { title == 'Google' }
    static content = {
        searchField { $("input[name=q]") }
        searchButton(to: GoogleResultsPage) { $("input[value='Buscar con Google']", 1) }
    }
}

class GoogleResultsPage extends Page {
    static at = { waitFor { title.endsWith("Buscar con Google") } }
    static content = {
        results(wait: true) { $("div.g") }
        result { index -> return results[index] }
        resultLink { index -> result(index).find("h3.r a") }
    }
}

class GoogleSearchSpec extends GebSpec {
    def 'go to google'() {
        when:
        to GoogleHomePage
    	searchField().value "Chuck Norris"
	    searchButton().click()

        then:
	    at GoogleResultsPage
    	resultLink(0).text().contains("Chuck")
   }
}
