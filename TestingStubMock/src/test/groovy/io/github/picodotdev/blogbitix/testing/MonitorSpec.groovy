package io.github.picodotdev.blogbitix.testing

import spock.lang.Specification

class MonitorSpec extends Specification {

    def "the monitor fires the alarm on high temp"() {
        given: "a sensor, a alarm and a max temperature"
        def sensor = Stub(Sensor)
        def alarm = Mock(Alarm)
        def maxTemperature = 35

        and: "a monitor"
        def monitor = new Monitor(sensor, alarm, maxTemperature)

        and: "three high temperatures"
        sensor.getTemperature() >>> [42, 45, 49]

        when:
        monitor.check()
        monitor.check()
        monitor.check()

        then:
        1 * alarm.fire()
    }
}
