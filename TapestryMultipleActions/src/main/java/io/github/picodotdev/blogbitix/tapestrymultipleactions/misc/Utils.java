package io.github.picodotdev.blogbitix.tapestrymultipleactions.misc;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.services.ProductRepository;
import org.apache.tapestry5.ioc.services.TypeCoercer;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Product> getProducts(String ids, TypeCoercer coercer, ProductRepository repository) {
        List<Product> products = new ArrayList<>();
        String[] sids = ids.split(",");
        for (String sid : sids) {
            Long id = coercer.coerce(sid, Long.class);
            Product p = repository.find(id);
            if (p != null) {
                products.add(p);
            }
        }
        return products;
    }
}
