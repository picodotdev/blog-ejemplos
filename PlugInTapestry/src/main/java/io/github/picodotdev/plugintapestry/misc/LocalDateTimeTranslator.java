package io.github.picodotdev.plugintapestry.misc;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.internal.translator.AbstractTranslator;
import org.apache.tapestry5.services.FormSupport;

public class LocalDateTimeTranslator extends AbstractTranslator<LocalDateTime> {

	private String patron;

	public LocalDateTimeTranslator(String patron) {
		super("localDateTime", LocalDateTime.class, "date-format-exception");
		this.patron = patron;
	}

	@Override
	public String toClient(LocalDateTime value) {
		if (value == null) {
			return null;
		}

		// Convertir el objeto date a su representación en String utilizando un patrón de fecha.
		return DateTimeFormatter.ofPattern(patron).format(value);
	}

	@Override
	public LocalDateTime parseClient(Field field, String clientValue, String message) throws ValidationException {
		if (clientValue == null) {
			return null;
		}

		try {
			// Convertir la represetación del objeto fecha en String a su representación en objeto
			// Date.
			return LocalDate.parse(clientValue, DateTimeFormatter.ofPattern(patron)).atStartOfDay();
		} catch (DateTimeParseException e) {
		    e.printStackTrace();
			throw new ValidationException(MessageFormat.format(message, field.getLabel()));
		}
	}

	@Override
	public void render(Field field, String message, MarkupWriter writer, FormSupport formSupport) {
	}
}