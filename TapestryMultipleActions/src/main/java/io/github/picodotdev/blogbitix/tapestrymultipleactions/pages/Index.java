package io.github.picodotdev.blogbitix.tapestrymultipleactions.pages;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.services.AppService;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.services.ProductRepository;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import java.util.List;
import java.util.stream.Collectors;

public class Index {

	@Property
	private Product product;

    @Inject
    private AppService service;

    @Inject
    private ProductRepository repository;

	@Inject
	private BeanModelSource beanModelSource;

	@Inject
	private ComponentResources resources;

    public List<Product> getProducts() {
        System.out.print(1);
    	return repository.findAll();
    }

	public BeanModel<Product> getModel() {
		BeanModel<Product> model = beanModelSource.createDisplayModel(Product.class, resources.getMessages());
		model.add("select", null).label("").sortable(false);
		model.add("action", null).label("Actions").sortable(false);
		model.include("select", "name", "stock", "state", "action");
        model.reorder("select", "name", "stock", "state", "action");
		return model;
	}

	public String getActions(Product product) {
	    return service.getAvaliableActions(product).stream().map(a -> a.name().toString().toLowerCase()).collect(Collectors.joining(","));
    }
}