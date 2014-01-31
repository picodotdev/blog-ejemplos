package io.github.picodotdev.log.markers;

import org.slf4j.Marker;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class ImportacionFilter extends Filter<ILoggingEvent> {

	@Override
	public FilterReply decide(ILoggingEvent event) {
		Marker marker = event.getMarker();
		if (Main.importacion.equals(marker) || marker.contains(Main.importacion)) {
			return FilterReply.ACCEPT;
		} else {
			return FilterReply.DENY;
		}
	}
}