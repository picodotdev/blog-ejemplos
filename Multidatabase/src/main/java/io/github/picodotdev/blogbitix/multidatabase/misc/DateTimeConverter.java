package io.github.picodotdev.blogbitix.multidatabase.misc;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.jooq.Converter;

public class DateTimeConverter implements Converter<Timestamp, DateTime> {

	private static final long serialVersionUID = 3505707535773076376L;

	@Override
    public DateTime from(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
        return new DateTime(timestamp);
    }

    @Override
    public Timestamp to(DateTime datetime) {
    	if (datetime == null) {
    		return null;
    	}
        return new Timestamp(datetime.getMillis());
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<DateTime> toType() {
        return DateTime.class;
    }
}