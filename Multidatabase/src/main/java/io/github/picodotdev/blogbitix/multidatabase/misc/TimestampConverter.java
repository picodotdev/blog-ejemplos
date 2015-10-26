package io.github.picodotdev.blogbitix.multidatabase.misc;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.jooq.Converter;

public class TimestampConverter implements Converter<Timestamp, LocalDateTime> {

	private static final long serialVersionUID = 3505707535773076376L;

	@Override
    public LocalDateTime from(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
        return timestamp.toLocalDateTime();
    }

    @Override
    public Timestamp to(LocalDateTime dateTime) {
    	if (dateTime == null) {
    		return null;
    	}
        return Timestamp.valueOf(dateTime);
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<LocalDateTime> toType() {
        return LocalDateTime.class;
    }
}