package io.github.picodotdev.blogbitix.entitiesid.domain.product;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, ProductId>, JpaSpecificationExecutor<Product>, CustomProductRepository {

    @Override
    @Modifying
    @Query("delete from Product")
    void deleteAll();
}