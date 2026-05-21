package io.github.picodotdev.blogbitix.tapestrymultipleactions.misc;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.services.ProductRepository;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.services.TypeCoercer;

public class ProductEncoder implements ValueEncoder<Product> {

	private ProductRepository repository;
	private TypeCoercer coercer;

	public ProductEncoder(ProductRepository repository, TypeCoercer coercer) {
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
	public Product toValue(String clientValue) {
		if (clientValue == null) {
			return null;
		}
		Long id = coercer.coerce(clientValue, Long.class);
		return repository.find(id);
	}
}