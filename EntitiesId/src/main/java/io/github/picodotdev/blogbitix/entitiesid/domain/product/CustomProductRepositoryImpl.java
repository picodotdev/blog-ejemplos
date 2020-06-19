package io.github.picodotdev.blogbitix.entitiesid.domain.product;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import javax.persistence.EntityManager;

public class CustomProductRepositoryImpl implements CustomProductRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public ProductId generateId() {
        BigInteger id = (BigInteger) entityManager.createNativeQuery("select nextval('product_id_seq')").getSingleResult();
        return ProductId.valueOf(id);
    }
}