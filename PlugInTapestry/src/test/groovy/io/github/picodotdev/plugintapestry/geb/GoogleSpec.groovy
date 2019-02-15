package io.github.picodotdev.plugintapestry.geb

import geb.spock.GebSpec
 
class GoogleSpec extends GebSpec {
    def 'go to google'() {
        when:
        go 'https://www.google.es'
 
        then:
        title == 'Google'
    }
}
