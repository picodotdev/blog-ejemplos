package io.github.picodotdev.blogbitix.tapestrymultipleactions.misc;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.services.ProductRepository;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.internal.translator.AbstractTranslator;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.FormSupport;

public class ProductTranslator extends AbstractTranslator<Product> {

	private ProductRepository repository;
	private TypeCoercer coercer;

	public ProductTranslator(ProductRepository repository, TypeCoercer coercer) {
		super("product", Product.class, "product-format-exception");
		this.repository = repository;
		this.coercer = coercer;
	}

	@Override
	public String toClient(Product value) {
		if (value == null) {
			return null;
		}
		return value.getId().toString();
	}

	@Override
	public Product parseClient(Field field, String clientValue, String message) throws ValidationException {
		if (clientValue == null) {
			return null;
		}
		Long id = coercer.coerce(clientValue, Long.class);
		return repository.find(id);
	}

	@Override
	public void render(Field field, String message, MarkupWriter writer, FormSupport formSupport) {
	}
}