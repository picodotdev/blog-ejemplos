package io.github.picodotdev.blogbitix.tapestrymultipleactions.components;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.misc.Utils;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.services.ProductRepository;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.List;

public class Actions {

    public enum Type {
        PRODUCT
    }

    @Parameter(defaultPrefix = BindingConstants.PROP)
    @Property
    private Product product;

    @Parameter
    private Type type;

    @Property
    private String ids;

    @Inject
    private ProductRepository repository;

    @Inject
    private TypeCoercer coercer;

    @Inject
    private Block productActionsBlock, productsActionsBlock;

    @Inject
    private RequestGlobals globals;

    @Environmental
    private JavaScriptSupport javascriptSupport;

    void afterRender() {
        if (product != null || type == Type.PRODUCT) {
            javascriptSupport.require("app/actions");
        }
    }

    void onSuccessFromProductForm() {
        repository.enable(product);
    }

    void onSuccessFromProductsForm() {
        repository.enable(getProducts());
    }

    public Block getBlock() {
        if (product != null) {
            return productActionsBlock;
        }
        switch (type) {
            case PRODUCT: return productsActionsBlock;
        }
        return null;
    }

    public String getEnabled(Product product) {
        return (product.isEnabled()) ? "disabled" : null;
    }

    public String getDisabled(Product product) {
        return (product.isDisabled()) ? "disabled" : null;
    }

    private List<Product> getProducts() {
        return Utils.getProducts(ids, coercer, repository);
    }
}
