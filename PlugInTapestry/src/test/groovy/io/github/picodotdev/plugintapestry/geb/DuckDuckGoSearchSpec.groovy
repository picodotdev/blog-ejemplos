package io.github.picodotdev.plugintapestry.geb

import geb.Page
import geb.spock.GebSpec

class DuckDuckGoHomePage extends Page {
    static url = 'https://duckduckgo.com/'
    static at = { title.startsWith('DuckDuckGo') }
    static content = {
        searchField { $("input[name=q]") }
        searchSubmit { $("input[type=submit]") }
    }
}

class DuckDuckGoResultsPage extends Page {
    static at = { waitFor { title.endsWith("DuckDuckGo") } }
    static content = {
        results(wait: true) { $("div[id=links] div.result") }
        result { index -> return results[index] }
        resultLink { index -> result(index).find("a") }
    }
}

class DuckDuckGoSearchSpec extends GebSpec {
    def 'go to duckduckgo'() {
        when:
        to DuckDuckGoHomePage
    	searchField().value "Chuck Norris"
        searchSubmit().click()

        then:
	    at DuckDuckGoResultsPage
    	resultLink(0)*.text().join(' ').contains("Chuck")
   }
}
