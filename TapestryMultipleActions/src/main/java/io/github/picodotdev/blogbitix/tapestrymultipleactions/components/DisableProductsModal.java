package io.github.picodotdev.blogbitix.tapestrymultipleactions.components;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.misc.Utils;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.services.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.List;
import java.util.stream.Collectors;

public class DisableProductsModal {

    @Property
    private String ids;

    @Property
    private ProductRepository.DisableReason reason;

    @Property
    private String url;

    @Inject
    private ProductRepository repository;

    @Inject
    private RequestGlobals requestGlobals;

    @Inject
    private TypeCoercer coercer;

    @Inject
    private Block reloadBlock;

    @Inject
    private ComponentResources componentResources;

    @Environmental
    private JavaScriptSupport javascriptSupport;

    @Component
    private Form form;

    @Component
    private Select select;

    @Component
    private Zone zone;

    public ProductRepository.DisableReason[] getModel() {
        return ProductRepository.DisableReason.values();
    }

    void setupRender() {
        url = componentResources.createEventLink("show").toAbsoluteURI();
    }

    public boolean isRender() {
        return requestGlobals.getRequest().isXHR();
    }

    public String getProductsLabel() {
        List<Product> products = getProducts();
        return String.format("%s (%s)", products.stream().map(p -> p.getName()).collect(Collectors.joining(", ")), products.size());
    }

    public Object onShow() {
        ids = requestGlobals.getRequest().getParameter("ids");
        return zone;
    }

    private void onValidateFromForm() {
        if (StringUtils.isBlank(ids)) {
            form.recordError("A product is required.");
        }
        if (reason == null) {
            form.recordError(select, "Reason is required.");
        }
    }

    private void onSuccessFromForm() {
        repository.disable(getProducts(), reason);
    }

    private Object onSubmitFromForm() {
        if (form.getHasErrors()) {
            return zone.getBody();
        }
        return reloadBlock;
    }

    private List<Product> getProducts() {
        return Utils.getProducts(ids, coercer, repository);
    }
}
