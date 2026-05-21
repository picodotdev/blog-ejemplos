package io.github.picodotdev.plugintapestry.misc;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.internal.translator.AbstractTranslator;
import org.apache.tapestry5.services.FormSupport;

public class DateTranslator extends AbstractTranslator<Date> {

	private String patron;

	public DateTranslator(String patron) {
		super("date", Date.class, "date-format-exception");
		this.patron = patron;
	}

	@Override
	public String toClient(Date value) {
		if (value == null) {
			return null;
		}

		// Convertir el objeto date a su representación en String utilizando un patrón de fecha.
		return new SimpleDateFormat(patron).format(value);
	}

	@Override
	public Date parseClient(Field field, String clientValue, String message) throws ValidationException {
		if (clientValue == null) {
			return null;
		}

		try {
			// Convertir la represetación del objeto fecha en String a su representación en objeto
			// Date.
			return new SimpleDateFormat(patron).parse(clientValue);
		} catch (ParseException e) {
			throw new ValidationException(MessageFormat.format(message, field.getLabel()));
		}
	}

	@Override
	public void render(Field field, String message, MarkupWriter writer, FormSupport formSupport) {
	}
}