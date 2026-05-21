package io.github.picodotdev.plugintapestry.geb

import geb.spock.GebSpec

class DuckDuckGoSpec extends GebSpec {
    def 'go to duckduckgo'() {
        when:
        go 'https://duckduckgo.com/'
 
        then:
        title.startsWith('DuckDuckGo')
    }
}
